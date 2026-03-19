docker run -d \
  --name pgvector \
  -e POSTGRES_DB=ai \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  ankane/pgvector