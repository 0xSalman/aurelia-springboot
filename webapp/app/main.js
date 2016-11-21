import {LogManager} from 'aurelia-framework';
import {ConsoleAppender} from 'aurelia-logging-console';
import environment from './environment';
import 'whatwg-fetch';

//Configure Bluebird Promises.
//Note: You may want to use environment-specific configuration.
Promise.config({
  warnings: {
    wForgottenReturn: false
  }
});

export function configure(aurelia) {
  aurelia.use
    .standardConfiguration()
  // .feature('resources')
  ;
  
  const logLevel = environment.debug ? LogManager.logLevel.debug : LogManager.logLevel.info;
  LogManager.addAppender(new ConsoleAppender());
  LogManager.setLevel(logLevel);
  
  if (environment.testing) {
    aurelia.use.plugin('aurelia-testing');
  }
  
  aurelia.start().then(() => aurelia.setRoot());
}
