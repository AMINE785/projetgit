import { Injectable } from '@angular/core';
import {Http} from '@angular/http';
import "rxjs/add/operator/map";
import { error } from 'protractor';
import {Client} from 'elasticsearch';
import { nextTick } from 'q';
@Injectable()
export class ChartserviceService {
  
  private client: Client;
  queryalldocs = {
    'query': {
      'match_all': {}
    }
  };
  constructor(public http:Http) {
   if (!this.client) {
      this.connect();
    }
  }
    connect() {
      this.client = new Client({
        host:'http://localhost:9200'
      }); 
    }
    getAllDocuments(_index,_type): any {
      return this.client.search({
        index: _index,  
        type:_type,
        body: this.queryalldocs,
        filterPath: ['hits.hits._source']
      });
        }

  }
