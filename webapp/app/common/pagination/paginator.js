import {inject, noView, processContent, customElement, bindable, LogManager} from 'aurelia-framework';
import {HttpService} from '../HttpService';

@noView()
@processContent(false)
@customElement('paginator')
@inject(HttpService)
export class Paginator {
  
  @bindable page = 0;
  @bindable pageSize = 25;
  @bindable uri;
  
  constructor(httpService: HttpService) {
    this.logger = LogManager.getLogger('paginator');
    this.httpService = httpService;
    this.pages = null;
    this.loading = false;
    this.data = [];
  }
  
  attached() {
    this.logger.debug('Paginator attached');
    return this.loadPage(this.page);
  }
  
  loadPage(page) {
    
    const uri = `${this.uri}?page=${this.page}&size=${this.pageSize}`;
    this.logger.debug(`Loading ${uri}`);
    return this.httpService.request(uri, {method: 'get'})
      .then(response => {
        this.data = response.content;
        this.pages = response.totalPages;
      })
      .catch(error => {
        this.logger.error(`failed to get pageable response from ${this.uri}`, error);
      });
  }
  
  pageChanged(newPage) {
    this.logger.debug(`Page changed to ${newPage}`);
    this.loadPage(this.page);
  }
}