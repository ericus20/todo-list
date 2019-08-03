# Specify a base image
FROM node:alpine

WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# Install some dependencies
COPY ./package.json ./
RUN npm install --loglevel=error
COPY ./ ./

#Default command
CMD ["npm", "run", "start-docker"]