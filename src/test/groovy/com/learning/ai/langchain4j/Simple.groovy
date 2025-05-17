package com.learning.ai.langchain4j

import dev.langchain4j.model.vertexai.VertexAiChatModel

class Simple {

    String call(String question) {
        VertexAiChatModel vertexAiChatModel = VertexAiChatModel.builder()
                .endpoint("us-central1-aiplatform.googleapis.com:443")
                .project("my-llm-java-demos")
                .location("us-central1")
                .publisher("google")
                .modelName("chat-bison@001")
                .temperature(1.0)
                .maxOutputTokens(256)
                .topK(40)
                .topP(0.95)
                .maxRetries(3)
                .build()

        def response = vertexAiChatModel.sendUserMessage(question)
        return response.text()
    }
}
