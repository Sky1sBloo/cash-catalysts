# Cash Catalysts
Cash Catalysts is a gamified Finance App designed to motivate users to save money through rewards and achievements.

## Planned Features
- Viewing and setting up transactions
- Tracking savings via interactive graphs
- Setting up goals
- Earn rewards for completing challenges and goal savings
  
## Requirements
- Java 21 or higher
- Gradle
- SQLite
- JUnit Jupiter

## Setup and Installation
1. Clone the repository
```
git clone https://github.com/Sky1sBloo/cash-catalysts.git
```
2. Run the project
```
./gradlew run
```

## Contributing
### Code Style Guidelines
 - The project uses [IntelliJ Idea's default format](https://pastebin.com/FnDCEDz7). The link is an Eclipse XML profile. 
 - camelCase for member functions and variables, PascalCase for class, record and interface names
 - Avoid systems hungarian

### Project Structure
 - All fxml files must be in their respective Resources/packagelink/forms
 - Non-essential files such as build files or ide settings must be kept out of the repository

### Branching Strategy
 - `main`: for development branch to simplify branching strategy and since it will only be released once
 - feature branches: must be named `feature/<feature name>`
 - hotfix branches: must be named `hotfix/<hotfix name>`
 - documentation branches: must be named `docs/<docs changes>`
 - Create new branch for every issue
 - Branches must be specific to that feature and only that feature (avoid side effects and major API changes as much as possible)

### Commit Messages
 - Maintain clear and understandable commit messages
 - Suggested format (Required on squash merge):
```
<type>: <subject>
``` 
- Type: `feat`, `fix`, `docs`, `test`, `refactor`, `chore`
- Type Descriptions:
  - `feat`: new features, functions, or classes
  - `fix`: bug fixes, small changes to existing code
  - `docs`: documentation updates
  - `test`: adding or updating tests
  - `refactor`: restructuring code without changing behavior
  - `chore`: non-functional tasks (e.g., configuration changes, version bumps)
- Example:
```
fix: database insertUser not returning the id

docs: added method descriptions on database handler
```
 - Detailed descriptions must be included in body

### Pull Request Guidelines
- Pull Request Format:
```
Title: <type>:<short summary of changes>
Body:
 - Summary
 - Related issues
   - Closes #13
   - Resolves #2
 - List of changes
 - Testing instructions
 - Additional notes
```

```
ex. 
Title: feat: Added reward system from accomplishing goals
Body:
This PR implements the reward system taken from accomplishing goals
Closes #13

Changes:
 - Added rewards class
 - Made rewards class as dependency on goals handler
 - Goals handler will call rewards on update() function
 - Added unit test on goals handler to test the reward function call

Testing instructions:
 - All Unit tests are in the `rewards` directory
 - Goals Handler tests are also modified in the `goalshandler` directory.
 - ./gradlew test

Additional Notes:
 - Feature is barebones and needs refinement
 - Other classes that uses goals handler may need to add the rewards dependency.
```
- All branches should be rebased from the `main` branch before submitting a pull request
- Pull request title must be clear and summary of changes must be included in the body.
- Major API changes and side effects must be mentioned in the pull request
- Ensure CI tools pass all checks before merging.

### Testing
- New features or components must be unit tested if applicable
- Avoid unit testing library/framework methods or functions with no logic

### Documentation
- Include Javadoc comments for:
  - All public classes or methods
  - Complex logic
### Code Reviews

- Reviewers must:
   - Check code quality if it follows standards
   - Run the branch locally to check its functionality
   - Ensure there are no breaking changes
- Use constructive feedback

### Security
- For JDBC, use prepared statement when retrieving user data rather than concatenating strings (to avoid sql injection)
- Passwords (if needed) must be hashed before storing them on the database.

## Testing
Unit Tests uses the JUnit Jupiter Framework. Unit tests are in the `app/src/test` directory.
