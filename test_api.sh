curl  --header "Content-type: application/json" \
  --request PUT \
  --data '{"id":"abcd12345", "data": "sample-value", "priority": 0}' \
  http://localhost:9000/kvstore