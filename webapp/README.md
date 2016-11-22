# Dotsub Assignment Webapp
A simple webapp that lets user upload files with some metadata (title, description and creation date), view files' metadata
with pagination and download files. Technology stack is Aurelia framework with Javascript ES6/7

## Requirements
- node & npm
- aurelia cli: `npm install -g aurelia-cli`

## Getting Started
1. `npm install`
2. `au build`
3. `au run`

## Notes

1. HTML5 compatible browsers are assumed. Website layout may not be 100% responsive
1. Webapp runs on port `6005`
2. It's assumed that target api runs on `localhost:6010`. To change target api, modify property `endpoints.server` in
`./aurelia_project/environments/dev.js`