import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-rentpage',
  templateUrl: './rentpage.component.html',
  styleUrls: ['./rentpage.component.css']
})
export class RentpageComponent {

  newdata: any;

  constructor(private _http: HttpClient) { }

  getallcycle() {
    return this._http.get('http://localhost:8080/api/cycles/list-data');
  }

  ngOnInit() {
    this.getallcycle().subscribe({
      next: (res) => {
        this.newdata = res;
        console.log('Success: Response from API:', this.newdata);
      },
      error: (error) => {
        console.error('Error: Failed to fetch data from API:', error);
      }
    });
  }

  borrowCycle(id: number, quantityToBorrow: number) {

    const requestBody = { id, count: quantityToBorrow };

    const url = `http://localhost:8080/api/cycles/${id}/borrow`;

    this._http.post(url, requestBody, { responseType: 'text' }).subscribe(response => {
      this.ngOnInit();
      console.log(`Cycle with ID ${id} rented successfully.`);
    });
  }

}
