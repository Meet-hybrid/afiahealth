\# Security Policy



\## Supported Versions

| Version | Supported |

|---|---|

| 1.0.x | Yes |



\## Reporting a Vulnerability

If you find a security issue in AfiaHealth:



1\. Do NOT open a public GitHub issue

2\. Email us at: security@afiahealth.africa

3\. Include a full description of the vulnerability

4\. We will respond within 48 hours

5\. We will credit you in our changelog



\## Security Features

\- JWT with refresh token rotation

\- BCrypt password hashing cost factor 12

\- SQL injection prevention via JPA parameterized queries

\- CORS restricted to allowed origins only

\- Rate limiting on all authentication endpoints

\- All admin actions logged to audit\_logs table

\- HTTPS enforced in production via Nginx

