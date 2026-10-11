# cargorent-user-service

Registration, login, JWT issuing, user profiles and roles (CUSTOMER / ADMIN). Seeds the first admin account on startup.

Part of **CarGoRent** – see [cargorent-infra](../cargorent-infra/README.md) for the architecture, how to run
the whole system, demo accounts and the Postman collection.

| | |
|---|---|
| Port | 8081 |
| Schema | `cargorent_users` (table `users`) |
| Swagger | http://localhost:8081/swagger-ui.html |

## Endpoints (behind the gateway prefix `/api`)

| Method | Path | Who |
|---|---|---|
| POST | `/auth/register`, `/auth/login` | public – returns a JWT |
| GET/PUT | `/users/me`, PUT `/users/me/password` | logged in |
| GET | `/users`, `/users/{id}`; PATCH `/users/{id}/active` | admin |

## Run

```bash
# needs MySQL: cd ../cargorent-infra && docker compose -f docker-compose.mysql.yml up -d
mvn spring-boot:run
```

Config: `JWT_SECRET` (must match the gateway), `ADMIN_EMAIL`, `ADMIN_PASSWORD`, `DB_HOST`, `DB_USER`, `DB_PASSWORD`.

## Package layout

`com.cargorent.<service>` → `controller` · `service` · `repository` · `entity` · `dto` · `client` (calls to other services) · `common` (errors, caller identity) · `config`
