import { Component, OnInit } from '@angular/core';
import { PricelistService } from '../_services/pricelist/pricelist.service';
import { Zone } from '../model/zone';

@Component({
  selector: 'app-pricelist-table',
  templateUrl: './pricelist-table.component.html',
  styleUrls: ['./pricelist-table.component.css']
})
export class PricelistTableComponent implements OnInit {

  private source: any[];
  private zoneList: Zone[];
  constructor(private priceListService: PricelistService) { }
  


  ngOnInit() {
    
  }

}
