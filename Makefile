run:
	docker-compose up -d --build

clean:
	docker image rm fg-framedata-api-sf6-api
	docker volume rm fg-framedata-api_fg-framedata-api_pgdata

stop:
	docker-compose down