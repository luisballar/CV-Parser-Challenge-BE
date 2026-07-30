# CV Parser & Reimagined Profile Viewer

A Small web application that takes any uploaded CV/résumé (PDF or DOCX), extracts the information from it, and displays that information back to the user in a completely different visual design than the original document.

## 🔗 Links

- **Deployed Application:** _(https://cv-parser-challenge-fe-iota.vercel.app/upload)_
- **BE Repository:** _(https://github.com/luisballar/CV-Parser-Challenge-BE)_
- **FE Repository:** _(https://github.com/luisballar/CV-Parser-Challenge-FE)_
- **Walkthrough:** _(https://drive.google.com/drive/folders/1CwsfyFC483R4DDxiIRey0IFhLcavie71?usp=sharing)_

## 🚀Stack Used

| Layer | Technology | Purpose |
|---|---|---|
| Frontend | Angular + TypeScript | User interface, routing, and API communication. |
| Backend | Java + Spring Boot | REST API for file processing and AI integration. |
| Extraction Method  | Google Gemini | Transforms extracted text into structured JSON. |
| Frontend Deployment | Vercel | Hosts the Angular application. |
| Backend Deployment | Railway | Hosts the Spring Boot API. |


## ⚙️ Setup / How to Run the Project Locally

### Prerequisites

#### Frontend
- Node.js 20+
- npm 10+
- Angular CLI 20+

#### Backend
- Java 21

> **Note:** Gradle does not need to be installed, as the project includes the Gradle Wrapper (`gradlew`).

### Installation & Running

#### Backend

```properties
GEMINI_API_KEY=YOUR_GEMINI_API_KEY
```

**Note:** Set `GEMINI_API_KEY` as an environment variable before running the backend. The key is intentionally excluded from the repository for security reasons. Spring Boot reads it automatically via `application.properties`.

**Linux/macOS**
```bash
export GEMINI_API_KEY=your_key
./gradlew bootRun
```

**Windows (CMD)**
```bash
set GEMINI_API_KEY=your_key
gradlew.bat bootRun
```

#### Frontend
#### Install dependencies
```bash
npm install
```

#### Run locally in development mode using the local backend API:

```bash
ng serve
```
The application starts at:
http://localhost:4200


#### Build and run the production version locally using the backend API deployed on Railway:

```bash
npm install -g serve
ng build --configuration production
serve -s dist/CV-Parser-Challenge-FE/browser
```

The application starts at:
http://localhost:3000


## 🏗️ Architecture

A layered architecture (`controller`, `service`, `domain`, `dto`, `mapper`, `api`, `config`) was implemented together with the `Strategy` pattern for the following reasons:

* **Layered Architecture:**
  * **Inversion of Control and Decoupling:** Keeps the HTTP layer (`controller`) isolated from the business logic (`service`) and the data representation (`dto`/`domain`). This improves maintainability and code readability.
  * **Security and Encapsulation:** Using DTOs and Mappers prevents exposing internal entities or domain models directly to the client.

* **Strategy Pattern:**
  * **Decoupling and Polymorphism:** Each file format (PDF, DOCX) has its own extraction strategy, allowing the system to select the appropriate implementation at runtime without relying on conditional logic.
  * **Maintainability and Flexibility:** Allows new file formats to be added in the future without modifying the existing system behavior.


### LLM Call Details (`GeminiClient`)

- **Model:** `gemini-3.5-flash-lite`, using the official `com.google.genai` SDK.

**Reason for Selection:**
- **Ease of Use and Cost:** It is simple and quick to integrate, while also being available at no cost.

- **Optimized for Cost and Latency:** The *flash-lite* variant was chosen because this is a text extraction task rather than one requiring complex reasoning, making it a better fit in terms of cost and response time.

- **Rate Limits:** Supports up to 500 API requests per day, 15 requests per minute, and up to 300k input tokens, which is more generous than the limits offered by many other models.

## 🧠 Data Extraction Approach

A **two-stage hybrid approach** is used:

1. **Raw (unstructured) text extraction** from the original file using specialized libraries for each format:
   - PDF → **Apache PDFBox** (`PDFTextStripper`).
   - DOCX → **Apache POI** (`XWPFWordExtractor`).

   Both implementations share a common `FileExtractionService` interface (`extractText` / `supports`), making it possible to add support for new file formats by simply creating a new implementation without modifying `CvProcessingService`.

2. **Structured data extraction** from the raw text using an **LLM (Google Gemini)** through `GeminiClient.extractResumeData(rawText)`. The model receives the plain text extracted from the CV and returns a structured JSON (personal information, work experience, education, skills, and certifications, language), which is then deserialized directly into the `Cv` domain object using Jackson. If the uploaded document is not a CV, it returns an error (NOT_A_CV).

### Trade-offs Considered

- - **Parsing library (PDFBox/POI) for text only, not structure:** These libraries are very reliable for extracting plain text from a file, but they do not understand semantics ("this is a job title", "this is a date"). For this reason, custom regex/heuristics were not used to structure the data. 

- **LLM for structuring:**
  - ✅ Handles the huge variability in formats and layouts of real CVs very well (columns, tables, different section ordering, different languages) without having to write and maintain rules/regex for each case.

  - ❌ Depends on an external service (latency, cost per request, availability - returning `503 Service Unavailable` if communication fails).

  - ❌ The response may vary slightly between executions; as it is not 100% deterministic, the LLM can also hallucinate, altering data.

- **Data Security and Privacy**
  - ✅ Stateless Processing (In-Memory) vs. Persistence: Since it does not store CVs or extracted JSONs in a database, it simplifies things by not implementing it.
  
  - ❌ No history or persistence: The user loses the preview if they reload the page, and there is no option to share via a permanent link (shareable URL).
  
## 🧪 AI Assistance

AI was used as a supporting tool during development; however, **all generated code was manually reviewed and adapted** to understand its functionality. This allowed correcting generated solutions that did not fit the project style or discarding code that went beyond the required scope.

### 🛠️ AI-Assisted and Manually Reviewed Tasks
* **Architecture and Initial Configuration:**
  * Layout and base structure of the project (Backend and Frontend).
  * Recommendations on libraries and technologies to use.

* **Backend:**
  * Generation of Domain models, DTOs, and Mappers.
  * Error and exception handling and resolution.
  * Initial implementation of the Gemini client based on the [Official Gemini SDK Documentation](https://github.com/googleapis/java-genai).
  * Methods for data structuring and parsing.
  * Automated Tests.

* **Frontend:**
  * Creation of Interceptors, Services, and Auxiliary classes.
  * CSS layout and visual design of the user interface (UI).
  * Validation methods for components.


 
## 🧪 Tests

**Linux/macOS**

```
./gradlew test
```

**Windows**

```
gradlew.bat test
```

## ⚠️ Known Limitations

- **Confidence indicators per field:** If the LLM extracts incorrect data but does not leave it as `null`, manual review does not detect it automatically (it only detects *missing* fields or fields with *invalid formats*, not *wrong* fields).
- No support for image + OCR (only PDF and DOCX).
- Does not handle files that are too large or have too many pages.
- As an LLM model, it can hallucinate and extract incorrect data.
- **Provider Dependency:** By delegating extraction to the LLM, response speed depends on the provider's API, making it susceptible to timeouts, rate limits, or service outages.
- It is not Serverless, so the system requires keeping instances running permanently, consuming resources.
- Deployments on Railway and Vercel have limited resources.



## 🚀 What would I improve if I had more time?

- Implementation of unit and integration tests.
- Field-level confidence indicators (for example, asking the LLM for a certainty score per field) to highlight not only what is *missing* but also what is *uncertain*.
- Persistence of the processed CV (the `Cv` domain already includes fields designed for this: `id`, `createdAt`, etc.) to generate a shareable link of the profile without having to re-upload the file.
- Moving the processing to serverless functions to process files in the background and/or avoid consuming as many resources.
