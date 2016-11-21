import {inject, LogManager} from 'aurelia-framework';
import 'semantic-ui-calendar';
import {HttpService} from '../common/HttpService';

@inject(HttpService)
export class UploadFile {
  
  constructor(httpService: HttpService) {
    
    this.logger = LogManager.getLogger('upload-file');
    this.httpService = httpService;
    this.title = '';
    this.description = '';
    this.creationTS = '';
    this.file = null;
    this.serverError = false;
    this.showSuccess = false;
  }
  
  attached() {
    
    $(this.calendar).calendar({
      onChange: (date, text) => {
        this.creationTS = date;
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
              prompt: 'Description must be at least 1 characters'
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
      userFile.append('creationTS', this.creationTS.toISOString());
      
      this.httpService.request('/file', {
        method: 'post',
        body: userFile
      }).then(response => {
        this.serverError = false;
        $(this.uploadForm).form('reset');
        this.showSuccess = true;
        setTimeout(() => {
          this.showSuccess = false;
        }, 1000)
      }).catch(error => {
        this.logger.error(error);
        this.serverError = true;
        this.showSuccess = false;
      });
    }
  }
}
