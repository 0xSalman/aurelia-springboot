import {inject, LogManager} from 'aurelia-framework';
import {HttpClient} from 'aurelia-fetch-client';
import environment from '../environment';

@inject(HttpClient)
export class HttpService {
  
  constructor(http: HttpClient) {
    this.http = http;
    this.logger = LogManager.getLogger('http-service');
    this.apiUrl = environment.endpoints.server;
  }
  
  request(resource, requestOptions) {
    this.logger.debug(`requestedResource=${resource}, method=${requestOptions.method}`);
    return this.http.fetch(this.apiUrl + resource, requestOptions)
      .then(response => {
        if (response.status >= 200 && response.status < 400) {
          return response.json().catch(() => null);
        }
        throw response;
      });
  }
}