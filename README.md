# Articles

- Spec: [gastivo/code-challenges](https://github.com/gastivo/code-challenges/tree/84d02087448a86c0d4ffdeb626c4d5d63c5e203a/java-spring)
- Requirements:
  - Maven 3.6.*
  - Java 21

## Usage
**Test:** `mvn test`  
**Build:** `mvn package`  
**Run:** `java -jar target/articles-0.0.1-SNAPSHOT.jar`


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


## Ham wer wieder was gelernt - Whitespace

Not all whitespace is the same. For the following characters `Characters::isWhitespace` is true while
`character.toString().trim().isEmpty()` is false:

```
'\u1680', // Ogham Space Mark
'\u2000', // En Quad
'\u2001', // Em Quad
'\u2002', // En Space
'\u2003', // Em Space
'\u2004', // Three-Per-Em Space
'\u2005', // Four-Per-Em Space
'\u2006', // Six-Per-Em Space
'\u2008', // Punctuation Space
'\u2009', // Thin Space
'\u200A', // Hair Space
'\u2028', // Line Separator
'\u2029', // Paragraph Separator
'\u205F', // Medium Mathematical Space
'\u3000'  // Ideographic Space
```

... and for some reason [Figure Space U+2007](https://en.wikipedia.org/wiki/Figure_space) is neither whitespace nor
trimmable.

**Takeaway:** Java is (again) very specific and one should probably use a library like [Guava](https://github.com/google/guava) for whitespace (and
weird character) detection and manipulation:
- [CharMatcher::whitespace](https://guava.dev/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#whitespace())
- [CharMatcher::trimFrom](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/base/CharMatcher.html#trimFrom(java.lang.CharSequence))

