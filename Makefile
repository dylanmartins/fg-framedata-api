run:
	docker-compose up -d --build

clean:
	@if docker image inspect fg-framedata-api-sf6-api > /dev/null 2>&1; then \
		docker image rm fg-framedata-api-sf6-api; \
	else \
		echo "Image fg-framedata-api-sf6-api does not exist."; \
	fi
	@if docker volume inspect fg-framedata-api_fg-framedata-api_pgdata > /dev/null 2>&1; then \
		docker volume rm fg-framedata-api_fg-framedata-api_pgdata; \
	else \
		echo "Volume fg-framedata-api_fg-framedata-api_pgdata does not exist."; \
	fi

stop:
	docker-compose down
