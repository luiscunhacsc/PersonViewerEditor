import React, { useEffect, useState } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { Paginator, PaginatorPageChangeEvent } from 'primereact/paginator';
import axios from 'axios';
import { classNames } from 'primereact/utils';
import 'primereact/resources/themes/lara-light-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

interface Person {
  id: number;
  firstName: string;
  lastName: string;
  birthDate: string;
  gender: 'MALE' | 'FEMALE' | 'OTHER' | 'NOT_SPECIFIED';
  email: string;
  phone: string;
  taxId: string;
  address: string;
}

interface Page {
  content: Person[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

const genderOptions = [
  { label: 'Todos', value: '' },
  { label: 'Masculino', value: 'MALE' },
  { label: 'Feminino', value: 'FEMALE' },
  { label: 'Outro', value: 'OTHER' },
  { label: 'Não especificado', value: 'NOT_SPECIFIED' }
];

export const PersonGrid: React.FC = () => {
  const [persons, setPersons] = useState<Person[]>([]);
  const [loading, setLoading] = useState(false);
  const [totalRecords, setTotalRecords] = useState(0);
  const [page, setPage] = useState(0);
  const [rows, setRows] = useState(10);
  const [sortField, setSortField] = useState('firstName');
  const [sortOrder, setSortOrder] = useState<1 | -1 | null>(1);
  const [globalFilter, setGlobalFilter] = useState('');
  const [genderFilter, setGenderFilter] = useState('');

  useEffect(() => {
    fetchPersons();
    // eslint-disable-next-line
  }, [page, rows, sortField, sortOrder, globalFilter, genderFilter]);

  const fetchPersons = async () => {
    setLoading(true);
    let params: any = {
      page: page,
      size: rows,
      sort: `${sortField},${sortOrder === 1 ? 'asc' : 'desc'}`
    };
    if (globalFilter) params['search'] = globalFilter;
    if (genderFilter && genderFilter !== 'Todos') params['gender'] = genderFilter;
    try {
      const response = await axios.get<Page>('http://localhost:8080/api/persons', {
        params
      });
      setPersons(response.data.content);
      setTotalRecords(response.data.totalElements);
    } catch (error) {
      setPersons([]);
      setTotalRecords(0);
    } finally {
      setLoading(false);
    }
  };

  const onPageChange = (event: PaginatorPageChangeEvent) => {
    setPage(event.page);
    setRows(event.rows);
  };

  const onSort = (e: any) => {
    setSortField(e.sortField);
    setSortOrder(e.sortOrder);
  };

  const header = (
    <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center gap-2">
      <span className="p-input-icon-left">
        <i className="pi pi-search" />
        <InputText
          type="search"
          onInput={(e: React.ChangeEvent<HTMLInputElement>) => setGlobalFilter(e.target.value)}
          placeholder="Pesquisar..."
          className="w-full"
        />
      </span>
      <Dropdown
        value={genderFilter}
        options={genderOptions}
        onChange={e => {
          if (e.value === '' || e.value === 'Todos') {
            setGenderFilter(''); // Estado inicial, sem filtro
          } else {
            setGenderFilter(e.value);
          }
        }}
        placeholder="Filtrar por género"
        className="w-full md:w-14rem"
      />
    </div>
  );

  return (
    <div className="card">
      <h2>Pessoas</h2>
      <DataTable
        value={persons}
        paginator={false}
        lazy
        loading={loading}
        totalRecords={totalRecords}
        sortField={sortField}
        sortOrder={sortOrder}
        onSort={onSort}
        header={header}
        responsiveLayout="scroll"
        emptyMessage="Nenhum registo encontrado."
        className="p-datatable-sm"
      >
        <Column field="firstName" header="Primeiro Nome" sortable filter filterPlaceholder="Pesquisar" />
        <Column field="lastName" header="Apelido" sortable />
        <Column field="birthDate" header="Data de Nascimento" sortable body={rowData => new Date(rowData.birthDate).toLocaleDateString()} />
        <Column field="gender" header="Género" sortable body={rowData => genderOptions.find(opt => opt.value === rowData.gender)?.label || rowData.gender} />
        <Column field="email" header="Email" sortable />
        <Column field="phone" header="Telefone" sortable />
        <Column field="taxId" header="NIF" sortable />
        <Column field="address" header="Morada" sortable />
      </DataTable>
      <Paginator
        first={page * rows}
        rows={rows}
        totalRecords={totalRecords}
        rowsPerPageOptions={[5, 10, 20, 50]}
        onPageChange={onPageChange}
        template="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
        className="mt-4"
      />
    </div>
  );
};

export default PersonGrid;
