import os
from google import genai

def test_gemini_connection():
    print("--- Gemini API Auto-Discovery Test ---")

    # 1. Get API Key
    api_key = os.getenv("GEMINI_API_KEY")
    if not api_key:
        api_key = input("Enter your Gemini API Key: ").strip()

    if not api_key:
        print("Error: API Key is missing.")
        return

    try:
        # 2. Initialize Client
        client = genai.Client(api_key=api_key)
        print("\n1. Authenticating...")

        # 3. Get the REAL list of models your key can access
        print("2. Fetching available models...")
        models_pager = client.models.list()
        all_models = [m.name for m in models_pager]

        # Print them so you can see what's actually available
        print(f"   Found {len(all_models)} models. Here are the 'Flash' ones:")
        flash_models = [m for m in all_models if "flash" in m and "vision" not in m]
        for m in flash_models:
            print(f"   - {m}")

        # 4. Smart Selection Logic
        # We look for specific stable versions if the alias 'gemini-1.5-flash' failed
        target_model = None

        # Priority 1: Specific stable version 001 or 002 (often fixes the 404)
        for candidate in ["gemini-1.5-flash-002", "gemini-1.5-flash-001"]:
            # Check if this candidate exists in the full list (ignoring 'models/' prefix)
            if any(candidate in m for m in all_models):
                target_model = candidate
                break

        # Priority 2: If specific versions aren't found, grab the first "1.5-flash" we saw
        if not target_model:
            target_model = next((m for m in flash_models if "1.5" in m), None)

        # Priority 3: Fallback to whatever flash model we found
        if not target_model and flash_models:
            target_model = flash_models[0]

        if not target_model:
            print("\n❌ Error: No 'Flash' models found in your account list.")
            print("Full list for debugging:", all_models)
            return

        # Clean up the name (remove 'models/' prefix if present, as the SDK handles it)
        clean_model_name = target_model.replace("models/", "")
        print(f"\n3. Testing with model: {clean_model_name}")

        # 5. Send Test Prompt
        response = client.models.generate_content(
            model=clean_model_name,
            contents="Hello! Reply with 'Connection Successful'."
        )

        print("\n✅ SUCCESS!")
        print(f"Response: {response.text}")

    except Exception as e:
        print("\n❌ FAILED.")
        print(f"Error details: {e}")


if __name__ == "__main__":
    test_gemini_connection()
