# Articles

- Spec: [gastivo/code-challenges](https://github.com/gastivo/code-challenges/tree/84d02087448a86c0d4ffdeb626c4d5d63c5e203a/java-spring)
- Requirements:
  - Maven 3.6.*
  - Java 21

## Usage
**Test:** `mvn test`  
**Build:** `mvn package`  
**Run:** `java -jar target/articles-0.0.1-SNAPSHOT.jar`  
**Build image:** `docker build -t ghcr.io/fmilkau/articles:latest .`  
**Run container:** ` docker run --rm -v /absolute/path/to/data/dir:/var/lib/articles -p 127.0.0.1:8080:8080 ghcr.io/fmilkau/articles:latest`

## API

**Endpoint:** `GET /articles/{id}`  
**Description:** Retrieves an article by its unique ID.

### Request Parameters
| Name  | Type   | Required | Description                       |
|-------|--------|----------|-----------------------------------|
| id    | String | Yes      | The ID of the article to retrieve |

### Responses

#### 200 OK
**Description:** Successfully retrieved the article.  
**Content-Type:** `application/json`  
**Example Response:**  
```json
{
  "articleId": "12345",
  "partnerId": "67890",
  "name": "Sample Article",
  "content": 10,
  "unit": "Box",
  "price": {
    "amount": 999,
    "currency": "EUR"
  }
}
```

#### 404 Not Found
**Description:** Article not found.  
**Content-Type:** `text/html`  
**Example Response (formatted for legibility):**   
```html
<html>
    <body>
        <h1>Whitelabel Error Page</h1>
        <p>This application has no configured error view, so you are seeing this as a fallback.</p>
        <div id='created'>Wed Feb 12 23:13:57 CET 2025</div>
        <div>[7e51de92-3] There was an unexpected error (type=Not Found, status=404).</div>
    </body>
</html>
```

