-- {{cookiecutter.project_name}} Database Initialization
-- This script runs when the PostgreSQL container starts for the first time

-- Create additional schemas if needed
-- CREATE SCHEMA IF NOT EXISTS analytics;
-- CREATE SCHEMA IF NOT EXISTS audit;

-- Set timezone
SET timezone = 'UTC';

-- Enable necessary extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create development and test databases (if needed)
-- Note: Creating databases from within another database requires superuser privileges
-- These can be created manually if needed:
--   CREATE DATABASE {{cookiecutter.project_slug.replace('-', '_')}}_dev;
--   CREATE DATABASE {{cookiecutter.project_slug.replace('-', '_')}}_test;

-- Log successful initialization
SELECT 'Database {{cookiecutter.project_slug.replace('-', '_')}} initialized successfully' as status;