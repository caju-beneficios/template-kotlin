"""
String transformation extensions for Jinja2 templates.

This module provides robust string transformations for cookiecutter templates,
focusing on proper camelCase and slug conversions with comprehensive edge case handling.
"""

import re
import unicodedata
from typing import Any
from jinja2.ext import Extension
from jinja2 import Environment


def to_camel_case(value: Any) -> str:
    """
    Convert a string to camelCase with comprehensive edge case handling.

    Handles various input formats:
    - PascalCase -> camelCase (Article -> article)
    - snake_case -> camelCase (user_profile -> userProfile)
    - kebab-case -> camelCase (user-profile -> userProfile)
    - space separated -> camelCase (User Profile -> userProfile)
    - Mixed formats -> camelCase (User_Profile-Name -> userProfileName)

    Args:
        value (str): Input string to convert

    Returns:
        str: camelCase version of the input
    """
    if not value or not isinstance(value, str):
        return value

    # Remove extra whitespace and normalize
    value = value.strip()

    if not value:
        return value

    # Split on common separators (underscores, hyphens, spaces)
    # Also split on transitions from lowercase to uppercase (PascalCase)
    parts = re.split(r'[_\-\s]+|(?<=[a-z])(?=[A-Z])', value)

    # Filter out empty parts and normalize each part
    parts = [part.strip() for part in parts if part.strip()]

    if not parts:
        return value

    # First part is lowercase, rest are title case
    camel_parts = []
    for i, part in enumerate(parts):
        if i == 0:
            camel_parts.append(part.lower())
        else:
            # Capitalize first letter, keep rest as lowercase
            camel_parts.append(part.capitalize())

    return ''.join(camel_parts)


def to_snake_case(value: Any) -> str:
    """
    Convert a string to snake_case.

    Args:
        value (str): Input string to convert

    Returns:
        str: snake_case version of the input
    """
    if not value or not isinstance(value, str):
        return value

    value = value.strip()

    # Insert underscores before uppercase letters that follow lowercase letters
    value = re.sub(r'([a-z0-9])([A-Z])', r'\1_\2', value)

    # Replace common separators with underscores
    value = re.sub(r'[_\-\s]+', '_', value)

    # Convert to lowercase and clean up multiple underscores
    value = re.sub(r'_+', '_', value.lower())

    return value.strip('_')


def to_kebab_case(value: Any) -> str:
    """
    Convert a string to kebab-case (slug).

    Args:
        value (str): Input string to convert

    Returns:
        str: kebab-case version of the input
    """
    if not value or not isinstance(value, str):
        return value

    # First convert to snake_case, then replace underscores with hyphens
    snake_value = to_snake_case(value)
    return snake_value.replace('_', '-')


def to_slug(value: Any, separator: str = '-') -> str:
    """
    Convert a string to a URL-friendly slug with comprehensive normalization.

    Features:
    - Unicode normalization (removes accents, etc.)
    - Handles special characters and punctuation
    - Customizable separator
    - Proper length handling

    Args:
        value (str): Input string to convert
        separator (str): Separator to use (default: '-')

    Returns:
        str: URL-friendly slug
    """
    if not value or not isinstance(value, str):
        return value

    # Normalize unicode characters (remove accents, etc.)
    value = unicodedata.normalize('NFKD', value)

    # Remove non-ASCII characters
    value = value.encode('ascii', 'ignore').decode('ascii')

    # Convert to lowercase
    value = value.lower()

    # Replace sequences of non-alphanumeric characters with separator
    value = re.sub(r'[^a-z0-9]+', separator, value)

    # Clean up multiple separators and trim
    value = re.sub(f'{re.escape(separator)}+', separator, value)
    value = value.strip(separator)

    return value


def to_pascal_case(value: Any) -> str:
    """
    Convert a string to PascalCase.

    Args:
        value (str): Input string to convert

    Returns:
        str: PascalCase version of the input
    """
    camel = to_camel_case(value)
    if not camel:
        return camel

    # Capitalize the first letter
    return camel[0].upper() + camel[1:] if len(camel) > 1 else camel.upper()


class StringExtension(Extension):
    """
    Jinja2 extension that adds string transformation filters.

    Available filters:
    - to_camel: Convert to camelCase
    - to_snake: Convert to snake_case
    - to_kebab: Convert to kebab-case
    - to_slug: Convert to URL-friendly slug
    - to_pascal: Convert to PascalCase
    """

    def __init__(self, environment: Environment) -> None:
        super().__init__(environment)

        # Register filters
        environment.filters['to_camel'] = to_camel_case
        environment.filters['to_snake'] = to_snake_case
        environment.filters['to_kebab'] = to_kebab_case
        environment.filters['to_slug'] = to_slug
        environment.filters['to_pascal'] = to_pascal_case




# Make StringExtension the default export for cookiecutter
# When cookiecutter imports 'extensions.str', it will get this class
__all__ = ['StringExtension']