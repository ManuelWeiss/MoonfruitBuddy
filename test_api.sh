# create a user
curl  --header "Content-type: application/json" \
  --request POST \
  --data '{"id": "mweiss@moonfruit.com", "name": "Manuel Weiss", "team": "Honeybadgers", "department": "development"}' \
  http://localhost:9000/users


# update a user
curl  --header "Content-type: application/json" \
  --request PUT \
  --data '{"id": "mweiss@moonfruit.com", "name": "Manuel Weiss", "team": "Honeybadgers", "department": "development"}' \
  http://localhost:9000/users