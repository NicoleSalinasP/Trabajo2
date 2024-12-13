import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDeparmento } from '../deparmento.model';
import { DeparmentoService } from '../service/deparmento.service';

@Component({
  standalone: true,
  templateUrl: './deparmento-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DeparmentoDeleteDialogComponent {
  deparmento?: IDeparmento;

  protected deparmentoService = inject(DeparmentoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deparmentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
