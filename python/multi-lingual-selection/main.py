import json
import os

# Sets the terminal path to this specific script, then defines the language folder
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
LANG_DIR = os.path.join(SCRIPT_DIR, "lang")

def get_languages():
    """Scans the lang folder and returns their codes"""
    if not os.path.exists(LANG_DIR):
        return []
    
    languages = []

    for filename in os.listdir(LANG_DIR):
        if filename.endswith(".json"):
            lang_code = filename[:-5]
            languages.append(lang_code)
    return languages

def load_language(lang_code):
    """Loads the .json file from the lang folder"""
    file_path = os.path.join(LANG_DIR, f"{lang_code}.json")

    with open(file_path, "r", encoding="utf-8") as file:
        return json.load(file)

def main():
    # Runs the scan
    current_langs = get_languages()
    # Checks if the folder is empty or was not found
    if not current_langs:
        print("ERROR: No language files found!")
        return
    
    # Gives the language choices as a string
    lang_options = ", ".join(current_langs)
    lang_code = "none"

    # Validates and defaults
    while lang_code not in current_langs:
        lang_code = input(f"\n| Select a language ({lang_options}): ").strip().lower()
        if lang_code not in current_langs:
            default_lang = input(f"ERROR: [{lang_code}] file not found. Default to English? (y/n): ").strip().lower()
            if default_lang == "y":
                if "en" in current_langs:
                    lang_code = "en"
                else:
                    print("ERROR: Default language not found!")
                    return
    
    try:
        translations = load_language(lang_code)
    except json.JSONDecodeError:
        # Fallback to english if the selected file is broken
        print(f"ERROR: [{lang_code}.json] is corrupted or missing. Defaulting to English.")
        try:
            translations = load_language("en")
        except:
            print("CRITICAL ERROR: Could not load any language files.")
            return

    msg = translations.get("hello", "ERROR: Missing JSON line: [hello]")
    print(f"\n{msg}")

if __name__ == "__main__":
    main()