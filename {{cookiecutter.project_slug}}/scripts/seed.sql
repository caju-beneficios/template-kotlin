-- {{cookiecutter.project_name}} Seed Data
-- This file contains sample data for development and testing

-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

{% if cookiecutter._resource_name_package == 'article' %}
-- Sample Articles
INSERT INTO {{cookiecutter._resource_name_package}} (id, version, title, content, status, created_at, modified_at)
VALUES
    (uuid_generate_v4(), 0, 'Getting Started with {{cookiecutter.project_name}}', 'This is a comprehensive guide to getting started with our application.', 'PUBLISHED', NOW(), NOW()),
    (uuid_generate_v4(), 0, 'Advanced Features Overview', 'Learn about the advanced features available in this system.', 'DRAFT', NOW(), NOW()),
    (uuid_generate_v4(), 0, 'Best Practices Guide', 'Follow these best practices for optimal performance and maintainability.', 'PUBLISHED', NOW(), NOW()),
    (uuid_generate_v4(), 0, 'API Documentation', 'Complete API documentation with examples and usage patterns.', 'REVIEW', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Log seed completion
SELECT 'Article seed data inserted successfully' as status;
{% else %}
-- TODO: Add seed data for {{cookiecutter._resource_name_package}} entity
-- INSERT INTO {{cookiecutter._resource_name_package}} (id, version, created_at, modified_at)
-- VALUES
--     (uuid_generate_v4(), 0, NOW(), NOW())
-- ON CONFLICT (id) DO NOTHING;

SELECT 'Seed data template ready for {{cookiecutter._resource_name_package}}' as status;
{% endif %}