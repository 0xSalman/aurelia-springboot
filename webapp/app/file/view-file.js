import {inject, LogManager} from 'aurelia-framework';
import {HttpService} from '../common/HttpService';
import {Paginator} from '../common/pagination/paginator';
import download from 'downloadjs';

@inject(HttpService, Paginator)
export class ViewFile {
  
  constructor(httpService: HttpService, paginator: Paginator) {
    
    this.logger = LogManager.getLogger('upload-file');
    this.httpService = httpService;
    
    paginator.uri = this.filesUri;
    this.paginator = paginator;
  }
  
  get filesUri() {
    return '/files';
  }
  
  refreshFilesList() {
    this.paginator.loadPage();
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