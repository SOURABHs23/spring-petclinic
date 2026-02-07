
---

# 🚀 How to Run the Application

## ✅ Prerequisites

- Java 17 or higher
- Maven (or use the included Maven wrapper)

---

## ▶️ Step 1: Clone the Repository

```bash
git clone https://github.com/your-repo/spring-petclinic.git
cd spring-petclinic
```

---

## ▶️ Step 2: Run the Application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or using Maven:

```bash
mvn spring-boot:run
```

---

## ▶️ Step 3: Access the Application

Application:
```
http://localhost:8080
```

H2 Database Console:
```
http://localhost:8080/h2-console
```

The application uses **H2 in-memory database by default** for development.

---

# 🏗 Feature Flag System Architecture

## 1️⃣ Database Storage

Feature flags are stored in a table:

```
feature_flags
```

Columns typically include:

- id
- key
- description
- enabled

Default flag values are preloaded from:

```
src/main/resources/db/h2/data.sql
```

---

## 2️⃣ Custom Annotation

Protected methods use a custom annotation:

```java
@FeatureToggle("feature-key")
```

Example:

```java
@FeatureToggle("add-new-pet")
public String processCreationForm(...) {
    ...
}
```

---

## 3️⃣ Spring AOP Enforcement

A Spring AOP aspect:

- Intercepts methods annotated with `@FeatureToggle`
- Checks the database for the flag state
- Blocks execution if disabled
- Throws `FeatureDisabledException`

This ensures:
- Controllers remain clean
- No repetitive if-check logic
- Clear separation of concerns

---

# 🛠 Design Decisions & Assumptions

## ✅ 1. Database-Based Flags

**Decision:** Store feature flags in a relational database.

**Reason:**
- Flags persist across restarts
- Can be modified at runtime
- No redeployment required
- Production-ready design

---

## ✅ 2. Use of Spring AOP

**Decision:** Use AOP instead of manual condition checks.

**Reason:**
- Cleaner controller code
- Centralized feature enforcement
- Easier to scale

---

## ✅ 3. REST API for Flag Management

Feature flags are managed through REST APIs.

Base path:

```
/api/flags
```

Supported operations:

- GET → List all flags
- POST → Create or update a flag
- DELETE → Delete a flag

This allows dynamic runtime control using Postman or curl.

---

## ✅ 4. H2 for Development

H2 in-memory database is used for simplicity.

Assumption:
In production, this can be replaced with:

- MySQL
- PostgreSQL

The schema supports relational databases.

---

# 📋 Feature Flags Implemented

The following features are protected:

| Feature Key   | Description                              | Implementation Location |
|---------------|------------------------------------------|--------------------------|
| owner-search  | Enables owner search form submission     | OwnerController :: processFindForm |
| add-new-pet   | Enables adding a new pet                 | PetController :: processCreationForm |
| add-visit     | Enables adding a visit                   | VisitController :: processNewVisitForm |

Default states are configured in:

```
src/main/resources/db/h2/data.sql
```

---

# 🎮 Managing Feature Flags

You can manage flags using any HTTP client.

---

## 🔹 1. Get All Flags

```
GET /api/flags
```

Example response:

```json
[
  {
    "id": 1,
    "key": "owner-search",
    "description": "Enable owner search",
    "enabled": true
  }
]
```

---

## 🔹 2. Create a Flag
**POST** `/api/flags`

Use this to create a NEW feature flag.

Request Body:
```json
{
  "key": "new-feature",
  "description": "Description of new feature",
  "enabled": true
}
```

---

## 🔹 3. Update a Flag
**PUT** `/api/flags/{key}`

Use this to update an EXISTING feature flag. You do not need to provide the ID.

Example: Update `add-new-pet` to be enabled.

**PUT** `/api/flags/add-new-pet`

Request Body:
```json
{
  "enabled": true,
  "description": "Enable add new pet (Updated)"
}
```

---

## 🔹 4. Delete a Flag

```
DELETE /api/flags/{key}
```

Example:

```
DELETE /api/flags/add-new-pet
```

---

# 🧪 Testing the Feature Toggle

1. Start the application.
2. Open: http://localhost:8080
3. Navigate to an Owner detail page.
4. Try adding a new pet.
   - By default, `add-new-pet` is disabled.
   - The request will be blocked.
5. Enable the feature:

```
PUT /api/flags/add-new-pet
```
 
Body:
 
```json
{
  "enabled": true
}
```

6. Try again — the feature now works without restarting the application.

---

# 🧪 Build the Project

To build the project:

```bash
./mvnw clean package
```

To format code:

```bash
./mvnw spring-javaformat:apply
```

---