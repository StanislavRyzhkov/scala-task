## Simple crawler

### На /api/titles посылается объект типа POST-запросом:

```json
{
  "urls": [
    "https://www.kommersant.ru/",
    "https://www.rbc.ru",
    "https://www.google.com",
    "https://www.baeldung.com",
    "https://www.forbes.ru/",
    "bad"
  ]
}
```

### Возвращается ответ типа:

```json
{
    "errors": [
        "Cannot determine request scheme and target endpoint as HttpMethod(GET) request to bad doesn't have an absolute URI"
    ],
    "urls": [
        "Коммерсантъ: последние новости России и мира",
        "Новости дня в России и мире — РБК",
        "Google",
        "Baeldung | Java, Spring and Web Development tutorials",
        "Forbes.ru | Бизнес, миллиардеры, новости, финансы, инвестиции, компании"
    ]
}
```

