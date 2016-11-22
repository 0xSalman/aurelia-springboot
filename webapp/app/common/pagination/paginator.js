import {inject, noView, processContent, customElement, bindable, LogManager} from 'aurelia-framework';
import {EventAggregator} from 'aurelia-event-aggregator';
import {HttpService} from '../HttpService';

@noView()
@processContent(false)
@customElement('paginator')
@inject(HttpService, EventAggregator)
export class Paginator {
  
  @bindable page = 0;
  @bindable pageSize = 25;
  @bindable uri;
  
  constructor(httpService: HttpService, evtAgg: EventAggregator) {
    this.logger = LogManager.getLogger('paginator');
    this.httpService = httpService;
    this.evtAgg = evtAgg;
    this.pages = null;
    this.loading = false;
    this.data = [];
  }
  
  attached() {
    this.logger.debug('Paginator attached');
    this.subscribe();
    return this.loadPage(this.page);
  }
  
  subscribe() {
    this.refresh = this.evtAgg.subscribe('refreshData', response => {
      this.logger.debug('received refresh data event', response);
      this.page = 0;
      this.loadPage(this.page);
    });
  }
  
  loadPage(page) {
  
    const uri = `${this.uri}?page=${page}&size=${this.pageSize}`;
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
  
  unsubscribe() {
    this.refresh.dispose();
  }
  
  detached() {
    this.unsubscribe();
  }
}