const PATIENTS = [
  {name:"Lúcia Monte Claro", age:62, unit:"USF Boa Vista", condition:"HAS e DM2", risk:"muito-alto", last:"Hoje, 08:40", context:"PA elevada"},
  {name:"Celina das Palmeiras", age:71, unit:"USF Cohab", condition:"DM2", risk:"alto", last:"12 jun, 10:15", context:"Em dia"},
  {name:"Marta Serra Azul", age:66, unit:"USF Maria Auxiliadora", condition:"HAS e DM2", risk:"alto", last:"08 jun, 09:30", context:"Exame pendente"},
  {name:"Antônio Vale Verde", age:54, unit:"USF Norte", condition:"HAS", risk:"moderado", last:"Ontem, 15:20", context:"Exame pendente"},
  {name:"João Ipê Amarelo", age:58, unit:"USF Norte", condition:"DM2", risk:"moderado", last:"06 jun, 11:45", context:"Em dia"},
  {name:"Raimundo Florêncio", age:48, unit:"USF Boa Vista", condition:"HAS", risk:"baixo", last:"10 jun, 14:05", context:"Em dia"},
  {name:"Neide Campo Belo", age:44, unit:"USF Cohab", condition:"Suspeita", risk:"baixo", last:"03 jun, 16:10", context:"Aguardando confirmação"}
];
const RISK_ORDER={"muito-alto":0,alto:1,moderado:2,baixo:3};
const RISK_LABEL={"muito-alto":"Muito alto",alto:"Alto",moderado:"Moderado",baixo:"Baixo"};
const $=id=>document.getElementById(id);
function patientInitials(name){return name.split(" ").slice(0,2).map(s=>s[0]).join("");}
function renderPatients(){
 const q=$("searchPatient").value.trim().toLocaleLowerCase("pt-BR");
 const risk=$("riskFilter").value, condition=$("conditionFilter").value, unit=$("unitFilter").value;
 const rows=PATIENTS.filter(p=>`${p.name} ${p.unit} ${p.condition}`.toLocaleLowerCase("pt-BR").includes(q) && (!risk||p.risk===risk) && (!condition||p.condition===condition) && (!unit||p.unit===unit)).sort((a,b)=>RISK_ORDER[a.risk]-RISK_ORDER[b.risk]);
 $("patientCount").textContent=`${rows.length} ${rows.length===1?"paciente":"pacientes"}`;
 const head='<div class="patient-row header"><span>Paciente</span><span>Condição</span><span>Última avaliação</span><span>Risco</span><span>Ação</span></div>';
 if(!rows.length){$("patientList").innerHTML=head+'<div class="empty">Nenhum paciente encontrado com esses filtros.</div>';return;}
 $("patientList").innerHTML=head+rows.map(p=>`<div class="patient-row data" role="button" tabindex="0" data-patient="${p.name}"><div class="person"><span class="avatar">${patientInitials(p.name)}</span><div><strong>${p.name}</strong><small>${p.age} anos · ${p.unit}</small></div></div><span class="hide-mobile">${p.condition}</span><span class="muted hide-mobile">${p.last}<br><small>${p.context}</small></span><span class="risk ${p.risk}">${RISK_LABEL[p.risk]}</span><span class="arrow-btn">Avaliar</span></div>`).join('');
 document.querySelectorAll('[data-patient]').forEach(row=>{const open=()=>{const p=PATIENTS.find(x=>x.name===row.dataset.patient);const params=new URLSearchParams({patient:p.name,age:p.age,unit:p.unit,condition:p.condition});window.location.href=`nova-avaliacao.html?${params.toString()}`;};row.addEventListener('click',open);row.addEventListener('keydown',e=>{if(e.key==='Enter'||e.key===' '){e.preventDefault();open();}});});
}
["searchPatient","riskFilter","conditionFilter","unitFilter"].forEach(id=>$(id).addEventListener(id==="searchPatient"?"input":"change",renderPatients));
document.addEventListener('keydown',e=>{if(e.key==='/'&&!['INPUT','SELECT','TEXTAREA'].includes(document.activeElement.tagName)){e.preventDefault();$('searchPatient').focus();}});
renderPatients();
