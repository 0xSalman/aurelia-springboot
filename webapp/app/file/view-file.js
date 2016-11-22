import {inject, LogManager} from 'aurelia-framework';
import {HttpService} from '../common/HttpService';
import download from 'downloadjs';

@inject(HttpService)
export class ViewFile {
  
  constructor(httpService: HttpService) {
    this.logger = LogManager.getLogger('view-file');
    this.httpService = httpService;
    this.filesUri = '/files';
  }
  
  attached() {
    this.logger.debug('ViewFile attached');
  }
  
  download(file) {
    
    this.logger.debug(`Requested to download file with id ${file.id}`);
    
    this.httpService.request(`/file/${file.id}`, {method: 'get'}, true)
      .then(data => {
        download(new Blob([data]), file.fileName, file.type);
      })
      .catch(error => {
        this.logger.error(error);
      });
  }
}