import {inject, LogManager} from 'aurelia-framework';

export class Home {
  
  constructor() {
    this.logger = LogManager.getLogger('home');
  }
  
  attached() {
    $('.menu .item').tab();
  }
}