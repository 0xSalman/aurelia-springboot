import {inject, LogManager} from 'aurelia-framework';
import {EventAggregator} from 'aurelia-event-aggregator';
import 'semantic-ui-calendar';
import {HttpService} from '../common/HttpService';

@inject(HttpService, EventAggregator)
export class UploadFile {
  
  constructor(httpService: HttpService, evtAgg: EventAggregator) {
    
    this.logger = LogManager.getLogger('upload-file');
    this.httpService = httpService;
    this.evtAgg = evtAgg;
    this.title = '';
    this.description = '';
    this.creationTS = '';
    this.file = null;
    this.serverError = false;
    this.showSuccess = false;
  }
  
  attached() {
  
    this.logger.debug('UploadFile attached');
    
    $(this.calendar).calendar({
      onChange: (date, text) => {
        this.creationTS = date.toJSON();
      }
    });
    
    $.fn.form.settings.rules.fileSize = (value) => {
      
      if (this.file) {
        const file = this.file[0];
        return file.size > 0;
      }
      
      return true;
    };
    
    $(this.uploadForm).form({
      fields: {
        title: {
          identifier: 'title',
          rules: [
            {
              type: 'empty',
              prompt: 'Title must have a value'
            },
            {
              type: 'minLength[1]',
              prompt: 'Title must be at least 1 characters'
            }
          ]
        },
        description: {
          identifier: 'description',
          rules: [
            {
              type: 'empty',
              prompt: 'Description must have a value'
            },
            {
              type: 'minLength[10]',
              prompt: 'Description must be at least 10 characters'
            }
          ]
        },
        creationTS: {
          identifier: 'creationTS',
          rules: [
            {
              type: 'empty',
              prompt: 'Creation Date must have a value'
            }
          ]
        },
        file: {
          identifier: 'file',
          rules: [
            {
              type: 'empty',
              prompt: 'Must select a file'
            },
            {
              type: 'fileSize',
              prompt: 'File cannot be empty'
            }
          ]
        }
      }
    });
  }
  
  upload() {
    
    if ($(this.loginForm).form('is valid')) {
      
      this.logger.debug(`title=${this.title}, description=${this.description}, creationTS=${this.creationTS}`);
      
      let userFile = new FormData();
      userFile.append('file', this.file[0]);
      userFile.append('title', this.title);
      userFile.append('description', this.description);
      userFile.append('creationTS', this.creationTS);
      userFile.append('type', this.file[0].type);
      
      this.httpService.request('/file', {
        method: 'post',
        body: userFile
      }).then(response => {
        this.serverError = false;
        $(this.uploadForm).form('reset');
        this.showSuccess = true;
        this.refreshFiles();
        setTimeout(() => {
          this.showSuccess = false;
        }, 1000)
      }).catch(error => {
        this.logger.error('failed to upload file', error);
        this.serverError = true;
        this.showSuccess = false;
      });
    }
  }
  
  refreshFiles() {
    this.logger.debug('sending refresh data event');
    this.evtAgg.publish('refreshData', true);
  }
}
