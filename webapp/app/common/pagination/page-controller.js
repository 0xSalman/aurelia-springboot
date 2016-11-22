import {inject, customElement, bindable, LogManager} from 'aurelia-framework';

@customElement('page-controller')
@inject(Element)
export class PageController {
  
  @bindable page;
  @bindable pages;
  @bindable loading = false;
  
  constructor(element) {
    this.logger = LogManager.getLogger('page-controller');
    this.element = element;
    this.useStartEdge = true;
    this.useEndEdge = true;
    this.edges = 2;
    this.displayedPages = 5;
    this.halfDisplayed = this.displayedPages / 2;
    this.displayablePages = [];
  }
  
  attached() {
    this.logger.debug('PageController attached');
  }
  
  pagesChanged(newValue) {
    this.logger.debug(`Pages changed to ${newValue}`);
    this.calculateDisplayablePages();
  }
  
  calculateDisplayablePages() {
    
    this.displayablePages = [];
    const interval = this.interval();
    
    if (interval.start > 0 && this.edges > 0) {
      if (this.useStartEdge) {
        const end = Math.min(this.edges, interval.start);
        this.appendPages(0, end);
      }
      
      if (this.edges < interval.start && (interval.start - this.edges != 1)) {
        this.appendPage();
      } else if (interval.start - this.edges == 1) {
        this.appendPage(this.edges);
      }
    }
    
    this.appendPages(interval.start, interval.end);
    
    if (interval.end < this.pages && this.edges > 0) {
      if (this.pages - this.edges > interval.end && (this.pages - this.edges - interval.end != 1)) {
        this.appendPage();
      } else if (this.pages - this.edges - interval.end == 1) {
        this.appendPage(interval.end);
      }
      
      if (this.useEndEdge) {
        var start = Math.max(this.pages - this.edges, interval.end);
        this.appendPages(start, this.pages);
      }
    }
  }
  
  interval() {
    return {
      start: Math.ceil(this.page > this.halfDisplayed ? Math.max(Math.min(this.page - this.halfDisplayed, (this.pages - this.displayedPages)), 0) : 0),
      end: Math.ceil(this.page > this.halfDisplayed ? Math.min(this.page + this.halfDisplayed, this.pages) : Math.min(this.displayedPages, this.pages))
    };
  }
  
  appendPages(start, end) {
    for (let i = start; i < end; i++) {
      this.appendPage(i);
    }
  }
  
  appendPage(index = -1) {
    const disabled = index === -1;
    const obj = {
      page: index,
      active: index === this.page,
      disabled: disabled
    };
    this.displayablePages.push(obj);
  }
  
  nextPage() {
    if (this.page < this.pages && !this.loading) {
      this.page++;
      this.calculateDisplayablePages();
    }
  }
  
  prevPage() {
    if (this.page > 0 && !this.loading) {
      this.page--;
      this.calculateDisplayablePages();
    }
  }
  
  goToPage(page) {
    this.page = page;
    this.calculateDisplayablePages();
  }
  
  get nextPageNo() {
    return Math.min(this.pages, this.page + 1);
  }
  
  get prevPageNo() {
    return Math.max(this.page - 1, 1);
  }
  
  get isFirstPage() {
    return this.page === 0;
  }
  
  get isLastPage() {
    return this.page === this.pages - 1 || !this.pages;
  }
}