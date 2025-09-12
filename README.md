# D287 – Java Frameworks
**Western Governors University**

---

## Repository Information
- **Working Branch URL:** [https://gitlab.com/wgu-gitlab-environment/student-repos/wmendo8/d287-java-frameworks.git](https://gitlab.com/wgu-gitlab-environment/student-repos/wmendo8/d287-java-frameworks.git)
- **Customer Chosen:** Elite Custom PC Builders – products are complete PC builds, and parts are components (CPU, GPU, RAM, SSD, etc.).

---

## Task A – Git Setup
- Project cloned into IntelliJ IDEA Ultimate (student license).
- Work completed on `working` branch.
- Commits made after each task with clear messages (see branch history below).

**Branch History:**  
Paste your `git log --oneline --decorate --graph --all` output here (you already captured it).

---

## Task B – README Notes (Changes for Tasks C–J)

### Task C – Customize HTML UI
- **File:** `mainscreen.html` (src/main/resources/templates/)
- **Line 6:** Updated shop name → `Elite Custom PC Builders – Inventory Management`.
- **Line 65:** Updated section header → `Custom PC Builds`.
- **All part/product names automatically displayed from DB.**

### Task D – Add “About” Page
- **File:** `about.html` (src/main/resources/templates/)
- Added shop description + navigation link back to `mainscreen`.
- **File:** `mainscreen.html` (line 11) → Added navigation link `<a th:href="@{/about}">About Us</a>`.

### Task E – Add Sample Inventory
- **File:** `DemoApplication.java` (src/main/java/com/example/demo/)
- Added Bootstrap code to insert 5 sample parts + 1 sample product **only if DB is empty**.

### Task F – Add “Buy Now” Button
- **File:** `mainscreen.html` (line 112) → Added `<form>` with hidden productID and “Buy Now” button.
- **File:** `AddProductController.java` (lines ~90–110) → Added `@PostMapping("/buyproduct")` to decrement inventory by 1 and show success/failure message.

### Task G – Add Min/Max Inventory to Parts
- **File:** `Part.java` (src/main/java/com/example/demo/domain/)
    - Lines 34–43 → Added `minInv` and `maxInv` fields with `@Min` validation.
- **File:** `InhousePartForm.html` & `OutsourcedPartForm.html`
    - Added new fields for `minInv` and `maxInv`.
- **File:** `application.properties`
    - Changed DB file name → `spring.datasource.url=jdbc:h2:file:~/spring-boot-h2-db102`.

### Task H – Add Validation
- **File:** `ValidInventory.java` + `InventoryValidator.java` (src/main/java/com/example/demo/validators/)
    - Enforces that `inv` is between `minInv` and `maxInv`.
- **File:** `InhousePartForm.html` & `OutsourcedPartForm.html`
    - Added `<div class="text-danger" th:if="...">` to display error messages.
- **File:** `Product.java` (validators `@ValidProductPrice` and `@ValidEnufParts` already applied).

### Task I – Add Unit Tests
- **File:** `PartTest.java` (src/test/java/com/example/demo/domain/)
    - Added tests for min/max inventory validation.
    - **New Tests:** `testMinMaxInventoryValid()` and `testMinMaxInventoryInvalid()`.

### Task J – Remove Unused Validators
- Removed any old/unused validator classes (only `ValidDeletePart`, `ValidInventory`, `ValidProductPrice`, and `ValidEnufParts` remain).

---

## Task K – Professional Communication
This README is written clearly with details for each change. All commit messages are professional and descriptive.

---

## Screenshots (Evidence for Tasks C–H)
- **C:** Customized UI showing “Elite Custom PC Builders” and product/part lists.
- **D:** About page screenshot with back navigation.
- **E:** Sample inventory auto-loaded (5 parts, 1 product).
- **F:** Buy Now button decrements product inventory.
- **G/H:** Validation error messages for parts (inv < min, inv > max) and products (price < sum of parts).
- **I:** Unit test run in IntelliJ (green check marks).

---

## Branch History Example (from `git log`)  
