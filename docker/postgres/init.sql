SELECT 'CREATE DATABASE facetrack'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'facetrack')\gexec