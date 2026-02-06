#!/bin/bash

# Script to replace all old cookiecutter variables with new semantic variables
# across all template files

echo "🔧 Fixing cookiecutter variables across all template files..."

TEMPLATE_DIR="{{cookiecutter.project_slug}}"

if [ ! -d "$TEMPLATE_DIR" ]; then
    echo "❌ Template directory not found: $TEMPLATE_DIR"
    exit 1
fi

echo "📁 Working in directory: $TEMPLATE_DIR"

# Function to safely replace variables in all files
replace_in_files() {
    local old_var="$1"
    local new_var="$2"
    local description="$3"

    echo "🔄 Replacing: $old_var → $new_var ($description)"

    # Find all .kt, .yml, .yaml, .sql files and replace
    find "$TEMPLATE_DIR" -type f \( -name "*.kt" -o -name "*.yml" -o -name "*.yaml" -o -name "*.sql" \) -exec sed -i.bak "s|$old_var|$new_var|g" {} \;

    # Remove backup files
    find "$TEMPLATE_DIR" -name "*.bak" -delete

    # Count occurrences to verify
    local count=$(find "$TEMPLATE_DIR" -type f \( -name "*.kt" -o -name "*.yml" -o -name "*.yaml" -o -name "*.sql" \) -exec grep -l "$new_var" {} \; | wc -l)
    echo "✅ Updated $count files"
}

echo ""
echo "🚀 Starting variable replacements..."
echo ""

# Replace old variables with new semantic variables
replace_in_files "{{cookiecutter\.resource_name}}" "{{cookiecutter._resource_name_class}}" "class names"
replace_in_files "{{cookiecutter\.resource_name_lower}}" "{{cookiecutter._resource_name_package}}" "package names"
replace_in_files "{{cookiecutter\._resource_name_camel}}" "{{cookiecutter._resource_name_class}}" "class references"
replace_in_files "{{cookiecutter\.resource_name\.capitalize()}}" "{{cookiecutter._resource_name_class}}" "capitalized class names"
replace_in_files "{{cookiecutter\.resource_name\.upper()}}" "{{cookiecutter._resource_name_constant}}" "constants"
replace_in_files "{{cookiecutter\.resource_name\.lower()}}" "{{cookiecutter._resource_name_package}}" "lowercase references"

# Replace plural variables
replace_in_files "{{cookiecutter\.resource_name_plural}}" "{{cookiecutter._resource_name_plural_class}}" "plural class names"
replace_in_files "{{cookiecutter\.resource_name_plural_lower}}" "{{cookiecutter._resource_name_plural_package}}" "plural package names"
replace_in_files "{{cookiecutter\.resource_name_plural_camel}}" "{{cookiecutter._resource_name_plural_class}}" "plural class references"
replace_in_files "{{cookiecutter\.resource_name_plural\.lower()}}" "{{cookiecutter._resource_name_plural_package}}" "plural lowercase"

echo ""
echo "✅ All variable replacements completed!"
echo ""
echo "🔍 Checking for any remaining old variables..."

# Check for any remaining old patterns
remaining=$(find "$TEMPLATE_DIR" -type f \( -name "*.kt" -o -name "*.yml" -o -name "*.yaml" -o -name "*.sql" \) -exec grep -l "resource_name[^_]" {} \; 2>/dev/null || true)

if [ -n "$remaining" ]; then
    echo "⚠️  Found files that may still contain old variables:"
    echo "$remaining"
else
    echo "✅ No remaining old variables found!"
fi

echo ""
echo "🎉 Template variable fix completed!"