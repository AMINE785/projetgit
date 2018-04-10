
import { Component,OnInit} from '@angular/core';
import {Http} from '@angular/http';
import { Chart } from 'chart.js';
import { ChartserviceService } from '../chartservice.service';
import { Client, SearchResponse } from "elasticsearch";
import {CustomerSource} from '../table-list/customer.interface';
import { JIRA } from '../table-list/jira.interface';
import { Model } from './model.model';
import{jira} from'./jira.model'

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  
    private static readonly INDEX1= 'pfevermeg';
    private static readonly TYPE= 'system_logs';
    private static readonly INDEX2 = 'pfevermegjira';
    nbr:number[]=[];
     mo:Model;
     list:string[]=[];
    customerSources:CustomerSource[];
    count:number=0;
    nbr2:number[]=[];
    jir:jira;
    list2:string[]=[];
    JIRAS:JIRA[];
    constructor(public http:Http,public chartserv:ChartserviceService) {
     
     }
    ngOnInit() {
    
      
      this.chartserv.getAllDocuments(DashboardComponent.INDEX1,DashboardComponent.TYPE)
      .then(response => { 
        this.customerSources = response.hits.hits;
       
        this.findList();
      }, error => {
        console.error(error);
      }).then(() => {
        console.log('Show Customer Completed!');
      });
  
      this.chartserv.getAllDocuments(DashboardComponent.INDEX2,DashboardComponent.TYPE)
      .then(response => { 
        this.JIRAS = response.hits.hits;
       
        this.findList();
      }, error => {
        console.error(error);
      }).then(() => {
        console.log('Show Customer Completed!');
      });
      
  }
  
    
  
  findList(){
    let model:Model[];
    let list:any[]=[];
  
    if(this.customerSources){
     
    for(let m of this.customerSources){
      
   if(m["_source"]["revision"] && m["_source"]["autheur"]){
   {
      if(list.indexOf(m["_source"]["revision"]) ==-1){
        list.push(m["_source"]["revision"] );
      }
    }
  }}}
  this.list=list;
  return list;
  
   }
   findOccurence(){
     let nbs:any[]=[];
  let list:any[];
  this.findList();
  
   if(this.customerSources){
     
    for(let i of this.list){
      let count=0;
      for(let m of this.customerSources){
      //model["user"]= m["_source"]["user"];
    //console.log("listtt "+i);
   if(m["_source"]["revision"] && m["_source"]["autheur"] && i==m["_source"]["revision"]){
   
     count ++;
   }
  }
   nbs.push(count);}
  }
  this.nbr=nbs;
  
  return this.nbr;
  
  }
  
  
  
   
   
   public doughnutChartLabels:string[] = this.list;
    public doughnutChartData:number[] = this.nbr;
     public doughnutChartType:string = 'doughnut';
    
    //events
   public chartClicked(e:any):void {
     console.log(e);
     }   
   
     public chartHovered(e:any):void {
      console.log(e);
     }







     findList2(){
      let ji:jira[];
      let list2:any[]=[];
    
      if(this.JIRAS){
       
      for(let m2 of this.JIRAS){
        
     if(m2["_source"]["key"] && m2["_source"]["name"]){
     {
        if(list2.indexOf(m2["_source"]["name"]) ==-1){
          list2.push(m2["_source"]["name"] );
        }
      }
    }}}
    this.list2=list2;
    return list2;
    
     }
     findOccurence2(){
       let nbs2:any[]=[];
    let list2:any[];
    this.findList2();
    
     if(this.JIRAS){
       
      for(let j of this.list2){
        let count2=0;
        for(let m2 of this.JIRAS){
        //model["user"]= m["_source"]["user"];
      //console.log("listtt "+i);
     if(m2["_source"]["name"] && m2["_source"]["key"] && j==m2["_source"]["name"]){
     
       count2 ++;
     }
    }
     nbs2.push(count2);}
    }
    this.nbr2=nbs2;
    
    return this.nbr2;
    
    }
    
    
    
     
     
     public doughnutChartLabel:string[] = this.list2;
      public doughnutChartDat:number[] = this.nbr2;
       public doughnutChartTyp:string = 'doughnut';
      
      //events
     public chartClicke(e:any):void {
       console.log(e);
       }   
     
       public chartHovere(e:any):void {
        console.log(e);
       }

  }
  
  