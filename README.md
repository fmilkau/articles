# Articles

- Spec: [gastivo/code-challenges](https://github.com/gastivo/code-challenges/tree/84d02087448a86c0d4ffdeb626c4d5d63c5e203a/java-spring)
- Requirements:
  - Maven 3.6.*
  - Java 21


## Questions
- How to handle double-digit prices (VK - artikel1.csv)?
- Is content == volume (liters)?


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

**Takeaway:** one should probably use a library like [Guava](https://github.com/google/guava) for whitespace detection
and manipulation:
- [CharMatcher::whitespace](https://guava.dev/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#whitespace())
- [CharMatcher::trimFrom](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/base/CharMatcher.html#trimFrom(java.lang.CharSequence))

