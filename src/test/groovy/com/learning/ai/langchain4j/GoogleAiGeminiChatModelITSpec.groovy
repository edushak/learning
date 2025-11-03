package com.learning.ai.langchain4j

import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.request.ResponseFormat
import dev.langchain4j.model.chat.response.ChatResponse
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel
import dev.langchain4j.model.output.TokenUsage
import org.junit.jupiter.api.Assertions
import spock.lang.IgnoreIf

import static com.learning.util.IOHelper.getFileText
import static org.assertj.core.api.Assertions.assertThat;

import spock.lang.Specification

@IgnoreIf({ !os.isWindows() && GoogleAiGeminiChatModelITSpec.keyPresent })
class GoogleAiGeminiChatModelITSpec extends Specification {

    static String model = 'gemini-2.0-flash'
    static String GOOGLE_AI_GEMINI_API_KEY = getFileText('.uncommitted')

    static boolean isKeyPresent() {
        GOOGLE_AI_GEMINI_API_KEY != null
    }

    def should_answer_simple_question() {
        given:
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(GOOGLE_AI_GEMINI_API_KEY)
                .modelName(model)
                .build();

        when:
        ChatResponse response = gemini.chat(ChatRequest.builder()
                .messages(UserMessage.from("What is the capital of France?"))
                .build());

        then:
        String text = response.aiMessage().text()
        assert text.contains("Paris")
    }

    void should_configure_generation() {
        given:
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(GOOGLE_AI_GEMINI_API_KEY)
                .modelName(model)
                .temperature(2.0)
                .topP(0.5)
                .topK(10)
                .maxOutputTokens(10)
                .build();

        when:
        ChatResponse response = gemini.chat(ChatRequest.builder()
            .messages(UserMessage.from("How much is 3+4? Reply with just the answer"))
            .build());

        then:
        String responseText = response.aiMessage().text()
        assert responseText.trim() == "7"
    }

    void should_answer_in_json() {
        given:
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()
            .apiKey(GOOGLE_AI_GEMINI_API_KEY)
            .modelName(model)
            .responseFormat(ResponseFormat.JSON)
            .logRequestsAndResponses(true)
            .build();

        when:
        ChatResponse response = gemini.chat(UserMessage.from(
                "What is the firstname of the John Doe?\n"
                + "Reply in JSON following with the following format: {\"firstname\": string}"));

        then:
        String jsonText = response.aiMessage().text();
        Assertions.assertTrue(jsonText.contains('"firstname"'));
        Assertions.assertTrue(jsonText.contains('"John"'));

        TokenUsage tokenUsage = response.tokenUsage();
        assertThat(tokenUsage.inputTokenCount()).isPositive();
        assertThat(tokenUsage.outputTokenCount()).isPositive();
        assertThat(tokenUsage.totalTokenCount())
                .isEqualTo(tokenUsage.inputTokenCount() + tokenUsage.outputTokenCount());
    }
}
