# Forms & Input

## AppTextField
**File:** `customviews/Forms.kt`

Styled `OutlinedTextField` with pre-configured focus/unfocus colours and a leading icon slot.

```kotlin
var text by remember { mutableStateOf("") }
AppTextField(
    text = text,
    placeholder = "Enter name…",
    onChange = { text = it },
    leadingIcon = { Icon(Icons.Rounded.Person, null) }
)
```

---

## MySearchBar
**File:** `customviews/SearchBar.kt`

Search bar with `Search`, `Clear`, and `Mic` action icons. Handles focus state internally.

```kotlin
MySearchBar(
    text = query,
    onTextChange = { query = it },
    placeHolder = "Search…",
    onCloseClicked = { query = "" },
    onSearchClicked = { /* submit */ },
    onMicClicked = { /* voice */ }
)
```

---

## DebouncedTextField
**File:** `customviews/texts/DebouncedTextField.kt`

See [Text Effects → DebouncedTextField](text-effects.md#debouncedtextfield).

---

## AutoSizeTextField / AutoSizableTextField
**File:** `customviews/EditTexts.kt`

Text fields that automatically shrink `fontSize` to fit the available width when the user's input is too long.

---

## DropdownSearch
**File:** `customviews/DropdownSearch.kt`

Searchable dropdown menu backed by a filterable list. The current implementation is mostly scaffolding — extend for production use.
