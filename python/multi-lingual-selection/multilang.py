# multilang.py
import json
import os

def get_languages(lang_dir):
    """Scans the lang folder and returns their codes"""
    if not os.path.exists(lang_dir):
        return []
    
    languages = []

    for filename in os.listdir(lang_dir):
        if filename.endswith(".json"):
            languages.append(filename[:-5])
    return languages

def load_language(lang_dir, lang_code):
    """Loads the .json file from the lang folder"""
    file_path = os.path.join(lang_dir, f"{lang_code}.json")
    with open(file_path, "r", encoding="utf-8") as file:
        return json.load(file)