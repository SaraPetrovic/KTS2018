import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { PricelistService } from '../_services/pricelist/pricelist.service';
import { DataSource } from '@angular/cdk/table';
import { Zone } from '../model/zone';
import { ZoneService } from '../_services/zones/zone.service';
import { Pricelist } from '../model/pricelist';
import { stringify } from '@angular/compiler/src/util';


@Component({
  selector: 'app-pricelist',
  templateUrl: './pricelist.component.html',
  styleUrls: ['./pricelist.component.css']
})
export class PricelistComponent implements OnInit {

  discountForm: FormGroup;
  priceForm: FormGroup;
  zoneList: Zone[];
  displayedColumns: string[] = ['name', 'value'];
  newPriceList: Pricelist;
  priceMap: any = {};

  constructor(private formBuilder: FormBuilder, private pricelistService: PricelistService, private zoneService: ZoneService) { }

  ngOnInit() {
    this.discountForm = this.formBuilder.group({
      lineDiscount: ['', [Validators.required, Validators.min(0)]],
      oneHourCoeffitient: ['', [Validators.required, Validators.min(0)]],
      monthlyCoeffitient: ['', [Validators.required, Validators.min(0)]],
      yearlyCoeffitient: ['', [Validators.required, Validators.min(0)]],
      studentDiscount: ['', [Validators.required, Validators.min(0)]],
      seniorDiscount: ['', [Validators.required, Validators.min(0)]],
      startDate: ['', Validators.required]
    });

    this.zoneService.getZones().subscribe(
      data => {
        this.zoneList = data;
      }, error => {
        console.log("wtf");
      }
    )
  }

  get f() {return this.discountForm.controls;}

  get f2() {return this.priceForm.controls;}

  addPricelist() {
    if (this.discountForm.invalid) {
      return;
    }

    this.newPriceList = new Pricelist();
    this.newPriceList.lineDiscount = this.f.lineDiscount.value;
    this.newPriceList.monthlyCoeff = this.f.monthlyCoeffitient.value;
    this.newPriceList.yearlyCoeff = this.f.yearlyCoeffitient.value;
    this.newPriceList.onehourCoeff = this.f.oneHourCoeffitient.value;
    this.newPriceList.seniorDiscount = this.f.seniorDiscount.value;
    this.newPriceList.studentDiscount = this.f.studentDiscount.value;
    this.newPriceList.startDate = this.f.startDate.value;

    
    this.zoneList.forEach(element => {
      var input = (<HTMLInputElement>document.getElementsByName(stringify(element.id))[0]);

      this.priceMap[input.name] = input.value;
    });

    this.newPriceList.zonePrices = this.priceMap;
    console.log(this.newPriceList);

    this.pricelistService.addPriceList(this.newPriceList).subscribe(
      data => {

        this.zoneList.forEach(element => {
          var input = (<HTMLInputElement>document.getElementsByName(stringify(element.id))[0]);
          input.value = "";
          
        });
        
        
        this.discountForm.reset();
        
        console.log('dodat hehe');
      }, error => {
        console.log('wtff');
      }
    )
  }


}
