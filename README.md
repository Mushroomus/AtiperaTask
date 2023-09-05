## GitHub Personal Access Token
Provide your GitHub Personal Access Token in "application.properties":
github.personalAccessToken=`<accessToken>`

Replace `<accessToken>` with your actual token.
GitHub webpage - https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens

## GET /github/{username}/repositories

Response
```JSON
[
  {
    "name": "apiMarkeplaceExtension",
    "ownerLogin": "Mushroomus",
    "branches": [
      {
        "name": "master",
        "lastCommitSha": "cd0e9d7b48ee089a6ba7f88ea4f5df6a6daac8a3"
      }
    ]
  },
  {
    "name": "DeansOfficeVue",
    "ownerLogin": "Mushroomus",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "4f1188f7cb2302480e7b2a89dd0eaae0e9f24d5e"
      }
    ]
  },
  {
    "name": "JavaDeansOfficeAPI",
    "ownerLogin": "Mushroomus",
    "branches": [
      {
        "name": "master",
        "lastCommitSha": "8ae8d49a41e51085f98969e187b098d0050cd3e1"
      }
    ]
  }
]
```

## Error Messages

User was not found
```JSON
{
  "status": 404,
  "message": "GitHub user not found: {username}"
}
```

Wrong media type
```JSON
{
  "status" : 406,
  "message" : "The requested media type 'application/xml' is not acceptable. Please use 'application/json'."
}
```

Unexpected error
```JSON
{
  "status" : 500,
  "message" : "An unexpected error occurred."
}
```

