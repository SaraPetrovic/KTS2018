import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PricelistService } from '../_services/pricelist.service';

@Component({
  selector: 'app-pricelist',
  templateUrl: './pricelist.component.html',
  styleUrls: ['./pricelist.component.css']
})
export class PricelistComponent implements OnInit {

  discountForm: FormGroup;
  priceForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private pricelistService: PricelistService) { }

  ngOnInit() {
    this.discountForm = this.formBuilder.group({
      lineDiscount: ['', [Validators.required, Validators.min(0)]],
      oneHourCoeffitient: ['', [Validators.required, Validators.min(0)]],
      monthlyCoeffitient: ['', [Validators.required, Validators.min(0)]],
      yearlyCoeffitient: ['', [Validators.required, Validators.min(0)]],
      studentDiscount: ['', Validators.required, Validators.min(0)],
      seniorDiscount: ['', [Validators.required, Validators.min(0)]]
    });

    //this.priceForm = this.formBuilder.group({
      // ovo dinamicki se pravi tabela na osnovu broja zona (parovi zone:price)
  //  })
  }

  get f() {return this.discountForm.controls;}

  addPricelist() {
    if (this.discountForm.invalid) {
      return;
    }
    
  }

}
