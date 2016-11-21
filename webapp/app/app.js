import {LogManager} from 'aurelia-framework';
import {Router} from 'aurelia-router';
import 'semantic-ui';

export class App {
  
  constructor() {
    this.logger = LogManager.getLogger('app');
  }
  
  configureRouter(config, router: Router) {
    this.router = router;
    config.title = 'Dotsub Assignment';
    config.options.pushState = true;
    
    config.map([
      {route: '', name: 'home', moduleId: 'home', nav: true, title: 'Home'},
    ]);
  }
}
