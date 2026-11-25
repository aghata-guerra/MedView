const CLINICAL_RULES = {
        source: "Regra ilustrativa – a validar com Linhas de Cuidado do MS",
        version: "0.3-demo",
        hypertension: {
          title: "Sinalização&nbsp;&nbsp;de HAS",
          systolicThreshold: 140,
          diastolicThreshold: 90,
          status: "proposta",
          description: "Sinaliza valores de PA acima de um limite simplificado para demonstração.",
        },
        diabetes: {
          title: "Sinalização de DM2",
          hba1cThreshold: 6.5,
          glucoseThreshold: 126,
          status: "proposta",
          description: "Sinaliza valores glicêmicos simplificados, sem confirmar diagnóstico.",
        },
        severity: {
          title: "Alerta&nbsp;&nbsp;de gravidade",
          systolicThreshold: 180,
          diastolicThreshold: 120,
          status: "proposta",
          description: "Destaca PA muito elevada associada a sinal de alerta informado.",
        },
        risk: {
          title: "Estratificação simplificada de risco",
          renalVeryHigh: 30,
          renalHigh: 60,
          hba1cHigh: 9,
          ageModerate: 60,
          status: "proposta",
          description: "Combina função renal, condições associadas, idade e marcadores simplificados.",
        },
        referral: {
          title: "Critérios ilustrativos de encaminhamento",
          conditions: ["drc", "retinopatia", "pe-diabetico", "has-resistente"],
          status: "validada",
          description: "Demonstra justificativa e prioridade para critérios selecionados.",
        },
        carePlan: {
          title: "Plano de cuidado ilustrativo",
          status: "publicada",
          description: "Organiza metas, exames, orientações não medicamentosas e seguimento.",
        },
      };

      const REQUIRED_FIELDS = {
        patientName: "Identificação",
        age: "Idade",
        unitName: "Unidade",
        condition: "Condição conhecida",
        systolic: "Pressão sistólica",
        diastolic: "Pressão diastólica",
        glucose: "Glicemia",
        hba1c: "HbA1c",
        weight: "Peso",
        height: "Altura",
        creatinine: "Creatinina",
        renal: "Função renal",
      };
const form=document.getElementById("assessmentForm");
const assessmentView=document.getElementById("assessment");
const resultView=document.getElementById("result");
let toastTimer; let decisionHistory=[];
function showToast(message){const toast=document.getElementById("toast");toast.textContent=message;toast.classList.add("show");clearTimeout(toastTimer);toastTimer=setTimeout(()=>toast.classList.remove("show"),2700);}
function showAssessment(){resultView.classList.remove("active");assessmentView.classList.add("active");window.scrollTo({top:0,behavior:"smooth"});}
function showResult(){assessmentView.classList.remove("active");resultView.classList.add("active");window.scrollTo({top:0,behavior:"smooth"});}
      function riskLabel(risk) {
        return { baixo: "Baixo", moderado: "Moderado", alto: "Alto", "muito-alto": "Muito alto" }[risk];
      }
function calculateBmi(){const weight=Number(form.elements.weight.value);const height=Number(form.elements.height.value);form.elements.bmi.value=weight&&height?(weight/height**2).toFixed(1):"";}
      function updateCompleteness() {
        const missing = Object.entries(REQUIRED_FIELDS).filter(([field]) => !form.elements[field].value);
        const completed = Object.keys(REQUIRED_FIELDS).length - missing.length;
        const percent = Math.round((completed / Object.keys(REQUIRED_FIELDS).length) * 100);
        document.getElementById("completionValue").textContent = `${percent}%`;
        document.getElementById("completionBar").style.width = `${percent}%`;
        document.querySelector(".meter").setAttribute("aria-valuenow", percent);
        document.getElementById("missingList").innerHTML = missing.length
          ? missing
              .map(
                ([field, label]) =>
                  `<li><button type="button" data-missing-field="${field}">${label}</button></li>`,
              )
              .join("")
          : "<li style='color:var(--green)'>Dados essenciais preenchidos</li>";
        document.querySelectorAll("[data-missing-field]").forEach((button) =>
          button.addEventListener("click", () => {
            const target = form.elements[button.dataset.missingField];
            target.scrollIntoView({ behavior: "smooth", block: "center" });
            window.setTimeout(() => target.focus(), 350);
          }),
        );
        document.getElementById("pendingTitle").textContent = missing.length
          ? `Pendências (${missing.length})`
          : "Tudo pronto";
        const identificationFields = ["patientName", "age", "unitName", "condition"];
        const measuresFields = ["systolic", "diastolic", "glucose", "hba1c", "weight", "height", "creatinine", "renal"];
        const identificationComplete = identificationFields.every((field) => form.elements[field].value);
        const measuresComplete = measuresFields.every((field) => form.elements[field].value);
        const stepLinks = document.querySelectorAll(".step-link");
        stepLinks[0].classList.toggle("complete", identificationComplete);
        stepLinks[1].classList.toggle("complete", measuresComplete);
        document.getElementById("stepStatus1").textContent = identificationComplete ? "Concluída" : "Pendente";
        document.getElementById("stepStatus2").textContent = measuresComplete ? "Concluída" : "Pendente";
        const note = document.getElementById("completionNote");
        note.textContent = missing.length
          ? "Dados insuficientes. Preencha os itens essenciais."
          : "Avaliação completa para classificação ilustrativa.";
        note.style.background = missing.length ? "var(--amber-bg)" : "var(--green-bg)";
        note.style.color = missing.length ? "#6d5521" : "var(--green)";
        return missing;
      }
form.addEventListener("input",()=>{calculateBmi();updateCompleteness();});
form.addEventListener("change",updateCompleteness);
document.querySelectorAll("[data-step-target]").forEach(button=>button.addEventListener("click",()=>document.getElementById(button.dataset.stepTarget).scrollIntoView({behavior:"smooth",block:"start"})));
form.querySelectorAll('[name="comorbidity"], [name="warning"]').forEach(checkbox=>checkbox.addEventListener("change",()=>{const group=checkbox.name;const noneValue=group==="warning"?"nenhum":"nenhuma";const inputs=[...form.querySelectorAll(`[name="${group}"]`)];if(checkbox.checked&&checkbox.value===noneValue){inputs.forEach(input=>{if(input!==checkbox)input.checked=false;});}else if(checkbox.checked){const none=inputs.find(input=>input.value===noneValue);if(none)none.checked=false;}}));
function fillForm(values){form.reset();Object.entries(values).forEach(([key,value])=>{if(form.elements[key])form.elements[key].value=value;});const drc=form.querySelector('[name="comorbidity"][value="drc"]');if(drc)drc.checked=true;const warning=form.querySelector('[name="warning"][value="dor-peito"]');if(warning)warning.checked=true;document.getElementById("contextPatient").textContent=values.patientName||"Nova avaliação";document.getElementById("contextDetails").textContent=values.unitName?`${values.unitName} · dados carregados para revisão`:"Atendimento em preenchimento";calculateBmi();updateCompleteness();}
const demoRecord={patientName:"Lúcia Monte Claro",age:62,unitName:"USF Boa Vista",condition:"ambas",systolic:186,diastolic:116,glucose:168,hba1c:8.8,weight:78,height:1.58,creatinine:1.7,renal:42,medications:"Medicamento A 10 mg; Medicamento B 500 mg"};
document.getElementById("importEsus").addEventListener("click",()=>{fillForm(demoRecord);showToast("Dados demonstrativos preenchidos. Revise antes de continuar.");});
document.getElementById("resetAssessment").addEventListener("click",()=>{if(!window.confirm("Deseja apagar os dados preenchidos e reiniciar esta avaliação?"))return;form.reset();decisionHistory=[];renderDecisionLog();document.getElementById("contextPatient").textContent="Nova avaliação";document.getElementById("contextDetails").textContent="Atendimento ainda não vinculado a um paciente";calculateBmi();updateCompleteness();showAssessment();form.elements.patientName.focus();});
      function getChecked(name) {
        return [...form.querySelectorAll(`[name="${name}"]:checked`)].map((input) => input.value);
      }

      function classify() {
        const data = Object.fromEntries(new FormData(form));
        const systolic = Number(data.systolic);
        const diastolic = Number(data.diastolic);
        const hba1c = Number(data.hba1c);
        const glucose = Number(data.glucose);
        const renal = Number(data.renal);
        const age = Number(data.age);
        const comorbidities = getChecked("comorbidity");
        const warnings = getChecked("warning").filter((item) => item !== "nenhum");
        const knownHas = ["has", "ambas"].includes(data.condition);
        const knownDm = ["dm2", "ambas"].includes(data.condition);
        const hasSignal =
          systolic >= CLINICAL_RULES.hypertension.systolicThreshold ||
          diastolic >= CLINICAL_RULES.hypertension.diastolicThreshold;
        const dmSignal =
          hba1c >= CLINICAL_RULES.diabetes.hba1cThreshold ||
          glucose >= CLINICAL_RULES.diabetes.glucoseThreshold;

        let classification = "Sem classificação confirmada";
        if ((knownHas || hasSignal) && (knownDm || dmSignal)) classification = "HAS e DM2";
        else if (knownHas) classification = "HAS";
        else if (knownDm) classification = "DM2";
        else if (hasSignal || dmSignal) classification = "Suspeita — requer confirmação profissional";

        const riskReasons = [];
        let riskScore = 0;
        if (renal < CLINICAL_RULES.risk.renalVeryHigh || comorbidities.includes("cardiovascular")) {
          riskScore = 3;
          riskReasons.push("Função renal muito reduzida ou doença cardiovascular informada.");
        } else if (
          renal < CLINICAL_RULES.risk.renalHigh ||
          hba1c >= CLINICAL_RULES.risk.hba1cHigh ||
          comorbidities.some((item) => ["drc", "retinopatia", "pe-diabetico", "has-resistente"].includes(item))
        ) {
          riskScore = 2;
          riskReasons.push("Marcador alterado ou condição associada informada.");
        } else if (age >= CLINICAL_RULES.risk.ageModerate || hasSignal || dmSignal) {
          riskScore = 1;
          riskReasons.push("Idade ou marcador clínico acima do limite ilustrativo.");
        } else {
          riskReasons.push("Sem marcador de maior risco nas regras simplificadas.");
        }
        const risk = ["baixo", "moderado", "alto", "muito-alto"][riskScore];
        const severe =
          (systolic >= CLINICAL_RULES.severity.systolicThreshold ||
            diastolic >= CLINICAL_RULES.severity.diastolicThreshold) &&
          warnings.length > 0;
        if (severe) {
          riskReasons.unshift("PA muito elevada associada a sinal de alerta informado.");
        }

        return {
          ...data,
          systolic,
          diastolic,
          hba1c,
          glucose,
          renal,
          age,
          comorbidities,
          warnings,
          hasSignal,
          dmSignal,
          classification,
          risk: severe ? "muito-alto" : risk,
          riskReasons,
          severe,
        };
      }
      function renderResult(data) {
        document.getElementById("resultPatient").textContent = data.patientName;
        document.getElementById("classification").textContent = data.classification;
        const why = [];
        why.push(`Condição registrada: ${form.elements.condition.options[form.elements.condition.selectedIndex].text}.`);
        why.push(`PA informada: ${data.systolic}/${data.diastolic} mmHg.`);
        why.push(`Glicemia ${data.glucose} mg/dL e HbA1c ${data.hba1c}%.`);
        why.push(`Aplicada versão ${CLINICAL_RULES.version}; ${CLINICAL_RULES.source}.`);
        document.getElementById("classificationWhy").innerHTML = why.map((item) => `<li>${item}</li>`).join("");
        document.getElementById("resultRisk").innerHTML = `
          <span class="risk ${data.risk}" style="font-size:16px;padding:8px 12px">${riskLabel(data.risk)}</span>`;
        document.getElementById("riskWhy").innerHTML = data.riskReasons
          .concat([CLINICAL_RULES.source])
          .map((item) => `<li>${item}</li>`)
          .join("");

        document.getElementById("severityAlert").innerHTML = data.severe
          ? `<div class="alert"><strong>Alerta de gravidade — avaliação imediata</strong>
              PA muito elevada associada a sinal de alerta informado. Este aviso é ilustrativo e não substitui protocolo, julgamento clínico ou serviço de urgência.</div>`
          : `<div class="alert info"><strong>Nenhum alerta de gravidade pelas regras demonstrativas</strong>
              Continue a avaliação clínica. Ausência de alerta no protótipo não exclui risco.</div>`;

        document.getElementById("goals").innerHTML = [
          "Definir meta de PA de forma individualizada pelo profissional.",
          "Definir meta glicêmica considerando contexto, funcionalidade e preferências.",
          "Acompanhar peso e função renal ao longo do cuidado.",
        ]
          .map((item) => `<li>${item}</li>`)
          .join("");

        const exams =
          data.classification === "Sem classificação confirmada"
            ? ["Sem indicação neste momento pelas regras demonstrativas. Reavaliar conforme julgamento clínico."]
            : [
                "Revisar exames disponíveis e periodicidade definida pelo profissional.",
                data.dmSignal ? "Avaliar rastreios relacionados ao diabetes conforme protocolo local." : "Sem indicação de rastreio adicional por DM neste momento.",
                data.renal < 60 ? "Reavaliar função renal conforme contexto clínico." : "Sem indicação renal adicional neste momento pela regra ilustrativa.",
              ];
        document.getElementById("exams").innerHTML = exams.map((item) => `<li>${item}</li>`).join("");
        document.getElementById("guidance").innerHTML = [
          "Construir plano alimentar possível e adequado ao contexto familiar.",
          "Conversar sobre atividade física segura e progressiva.",
          "Reforçar autocuidado, sinais de alerta e retorno programado.",
        ]
          .map((item) => `<li>${item}</li>`)
          .join("");
        document.getElementById("followup").innerHTML = [
          data.risk === "muito-alto" || data.risk === "alto"
            ? "Seguimento mais próximo, com prazo definido pelo profissional."
            : "Seguimento programado na APS, conforme avaliação profissional.",
          "Confirmar compreensão e pactuar o próximo contato.",
        ]
          .map((item) => `<li>${item}</li>`)
          .join("");

        const referralMap = {
          drc: ["Doença renal crônica informada", "prioridade alta"],
          retinopatia: ["Retinopatia informada", "prioridade programada"],
          "pe-diabetico": ["Pé diabético informado", "prioridade alta"],
          "has-resistente": ["HAS resistente informada", "prioridade programada"],
        };
        const referrals = data.comorbidities.filter((item) => referralMap[item]);
        document.getElementById("referral").innerHTML = referrals.length
          ? referrals
              .map(
                (item) =>
                  `<div class="alert" style="margin-bottom:9px"><strong>Considerar encaminhamento — ${referralMap[item][1]}</strong>
                  Justificativa: ${referralMap[item][0]}. Confirmar critérios e fluxo local antes da decisão.</div>`,
              )
              .join("")
          : `<div class="alert info"><strong>Manejo possível na APS</strong>Nenhum critério demonstrativo de encaminhamento foi identificado. Manter cuidado longitudinal conforme avaliação profissional.</div>`;

        const recommendations = [
          "Registrar metas individualizadas no plano de cuidado.",
          referrals.length ? "Avaliar encaminhamento conforme critério e fluxo local." : "Manter seguimento longitudinal na APS.",
          "Pactuar orientações não medicamentosas com a pessoa atendida.",
          "Definir prazo de retorno segundo risco e contexto clínico.",
        ];
        document.getElementById("decisionList").innerHTML = recommendations
          .map(
            (item, index) => `<div class="decision" data-decision="${index}">
              <p>${item}</p>
              <div class="decision-actions">
                <button data-action="Aceita">Aceitar</button>
                <button data-action="Editada">Editar</button>
                <button data-action="Rejeitada">Rejeitar</button>
              </div>
            </div>`,
          )
          .join("");
        document.querySelectorAll("[data-action]").forEach((button) =>
          button.addEventListener("click", handleDecision),
        );
        decisionHistory = [];
        renderDecisionLog();
      }
      function handleDecision(event) {
        const action = event.currentTarget.dataset.action;
        const row = event.currentTarget.closest(".decision");
        const recommendation = row.querySelector("p").textContent;
        let detail = "";
        if (action === "Rejeitada") {
          detail = window.prompt("Informe o motivo obrigatório para rejeitar:");
          if (!detail || !detail.trim()) {
            showToast("Rejeição cancelada: informe um motivo.");
            return;
          }
        }
        if (action === "Editada") {
          detail = window.prompt("Descreva a edição feita pelo profissional:");
          if (!detail || !detail.trim()) {
            showToast("Edição cancelada: descreva a alteração.");
            return;
          }
          row.querySelector("p").textContent = detail.trim();
        }
        row.classList.remove("accepted", "edited", "rejected");
        row.classList.add({ Aceita: "accepted", Editada: "edited", Rejeitada: "rejected" }[action]);
        decisionHistory.unshift({
          action,
          recommendation,
          detail: detail.trim(),
          time: new Date().toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" }),
        });
        renderDecisionLog();
        showToast(`Decisão registrada: ${action.toLowerCase()}.`);
      }

      function renderDecisionLog() {
        const log = document.getElementById("decisionLog");
        if (!decisionHistory.length) {
          log.innerHTML = '<p class="muted">Nenhuma decisão registrada nesta sessão.</p>';
          return;
        }
        log.innerHTML = decisionHistory
          .map(
            (entry) => `<div class="log-entry">
              <strong>${entry.action}</strong> — ${entry.recommendation}
              ${entry.detail ? `<br><span>Registro do profissional: ${entry.detail}</span>` : ""}
              <time>Hoje, ${entry.time} · sessão demonstrativa</time>
            </div>`,
          )
          .join("");
      }
document.getElementById("analyze").addEventListener("click",()=>{const missing=updateCompleteness();if(missing.length){showToast(`Faltam ${missing.length} campos essenciais. Preencha o primeiro item pendente.`);const first=form.elements[missing[0][0]];first.scrollIntoView({behavior:"smooth",block:"center"});first.focus();return;}renderResult(classify());showResult();});
document.getElementById("acceptAll").addEventListener("click",()=>{const pending=[...document.querySelectorAll(".decision:not(.accepted) [data-action='Aceita']")];pending.forEach(button=>button.click());if(!pending.length)showToast("Todas as recomendações já foram aceitas.");});
function finishCare(){const undecided=document.querySelectorAll(".decision:not(.accepted):not(.edited):not(.rejected)").length;if(undecided&&!window.confirm(`Ainda há ${undecided} recomendações sem decisão. Concluir mesmo assim?`))return;showToast("Atendimento demonstrativo concluído.");setTimeout(()=>window.location.href="pacientes.html",450);}
document.getElementById("finishCare").addEventListener("click",finishCare);document.getElementById("finishCareBottom").addEventListener("click",finishCare);
// Abre a avaliação com dados básicos ao chegar pela fila de pacientes.
const params=new URLSearchParams(window.location.search);
if(params.has("patient")){fillForm({patientName:params.get("patient"),age:params.get("age")||"",unitName:params.get("unit")||"",condition:({"HAS e DM2":"ambas","HAS":"has","DM2":"dm2"})[params.get("condition")]||""});}
calculateBmi();updateCompleteness();