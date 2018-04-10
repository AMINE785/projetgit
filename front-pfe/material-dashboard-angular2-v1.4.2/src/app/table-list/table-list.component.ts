import { Component} from '@angular/core';
import {Http} from '@angular/http';
import { ChartserviceService } from '../chartservice.service';
import { Observable } from 'rxjs';
import { Client, SearchResponse } from "elasticsearch";

import {CustomerSource} from './customer.interface';
import {JIRA} from './jira.interface';

@Component({
  selector: 'app-table-list',
  templateUrl: './table-list.component.html', 
  styleUrls: ['./table-list.component.css']
})
export class TableListComponent  {
    private static readonly INDEX1 = 'pfevermeg';
    private static readonly TYPE1= 'system_logs';
    private static readonly INDEX2 = 'pfevermegjira';
    
    
    customerSources:CustomerSource[]; 
  JIRAS:JIRA[];
    constructor(public http:Http,public chartserv:ChartserviceService) { }
    ngOnInit() {
      
      this.chartserv.getAllDocuments(TableListComponent.INDEX1,TableListComponent.TYPE1)
      
      .then(response => { 
        this.customerSources = response.hits.hits;
        console.log(response.hits.hits);
      }, error => {
        console.error(error);
      }).then(() => {
        console.log('Show Customer Completed!');
      });
 


      this.chartserv.getAllDocuments(TableListComponent.INDEX2,TableListComponent.TYPE1)
      
      .then(response => { 
        this.JIRAS = response.hits.hits;
        console.log(response.hits.hits);
      }, error => {
        console.error(error);
      }).then(() => {
        console.log('Show Customer Completed!');
      });
  }

}
