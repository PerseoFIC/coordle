<template>
	<v-app>
		<b-container id="contenedor-titulo" class="text-center position-relative border-bottom mb-3">
			<div id="icono-info" class="position-absolute izquierda">
				<img src="/icons/info-circle.svg" width="32" height="32" @click="mostrarInfo">
			</div>
			<h1 class="text-center mb-0 centrado-vertical">COORDLE</h1>
			<div id="icono-estadisticas" class="position-absolute derecha">
				<img src="/icons/clipboard-data.svg" width="32" height="32" @click="mostrarEstadisticas">
			</div>
			<div id="icono-ayuda" class="position-absolute derecha">
				<img src="/icons/patch-question.svg" width="32" height="32" @click="mostrarAyuda">
			</div>
		</b-container>
		<b-container id="contenedor-busqueda">
			<img width="128" height="128" :src="this.getURLImagen()"/>
			<p class="capital-busqueda">{{ nombreCiudad }} - {{ territorioCiudad }}</p>
		</b-container>
		<b-container id="contenedor-celdas">
			<b-row v-for="(ef, indicef) in 6" v-bind:key="indicef">
				<b-col v-for="(ec, indicec) in 7" v-bind:key="8 * indicef + indicec"><b-form-input v-model="celdas[8 * indicef + indicec]" :disabled="true" :state="estado[8 * indicef + indicec]"/></b-col>
				<b-col><b-button class="boton-direccion" :disabled="true">
					<img :src="direccionesPosibles[direcciones[indicef]]" width="32" height="32">
				</b-button></b-col>
			</b-row>
		</b-container>
		<b-container id="matriz-botones">
			<b-row>
				<b-col><b-button @click="setValor('0')" :disabled="isDisabled('0')">0</b-button></b-col>
				<b-col><b-button @click="setValor('1')" :disabled="isDisabled('1')">1</b-button></b-col>
				<b-col><b-button @click="setValor('2')" :disabled="isDisabled('2')">2</b-button></b-col>
				<b-col><b-button @click="setValor('3')" :disabled="isDisabled('3')">3</b-button></b-col>
				<b-col><b-button @click="setValor('4')" :disabled="isDisabled('4')">4</b-button></b-col>
				<b-col><b-button @click="setValor('N')" :disabled="isDisabled('N')">N</b-button></b-col>
				<b-col><b-button @click="setValor('E')" :disabled="isDisabled('E')">E</b-button></b-col>
				<b-col><b-button @click="enviar()" :disabled="isDisabled('Enviar')">Enviar</b-button></b-col>
			</b-row>
			<b-row>
				<b-col><b-button @click="setValor('5')" :disabled="isDisabled('5')">5</b-button></b-col>
				<b-col><b-button @click="setValor('6')" :disabled="isDisabled('6')">6</b-button></b-col>
				<b-col><b-button @click="setValor('7')" :disabled="isDisabled('7')">7</b-button></b-col>
				<b-col><b-button @click="setValor('8')" :disabled="isDisabled('8')">8</b-button></b-col>
				<b-col><b-button @click="setValor('9')" :disabled="isDisabled('9')">9</b-button></b-col>
				<b-col><b-button @click="setValor('S')" :disabled="isDisabled('S')">S</b-button></b-col>
				<b-col><b-button @click="setValor('O')" :disabled="isDisabled('O')">O</b-button></b-col>
				<b-col><b-button @click="borrar()" :disabled="isDisabled('Borrar')">Borrar</b-button></b-col>
			</b-row>
		</b-container>
		<div class="d-block text-center" v-if="!isDisabledNuevaPartida">
			<b-button pill class="mt-3" variant="outline-success" @click="nuevaPartida" :disabled="isDisabledNuevaPartida">Nueva partida</b-button>
		</div>
		<div>
			<b-modal id="modalVictoria" size="sm" hide-footer title="Fin de la partida">
				<div class="d-block text-center">
					<h3>¡Victoria!</h3>
				</div>
				<div class="d-block text-center">
					<b-button pill class="mt-3" variant="outline-success" @click="nuevaPartida">Nueva partida</b-button>
					<b-button pill class="mt-3" variant="outline-info" @click="ocultarVictoria">Cerrar</b-button>
				</div>
			</b-modal>
		</div>
		<div>
			<b-modal id="modalDerrota" size="sm" hide-footer title="Fin de la partida">
				<div class="d-block text-center">
					<h3>¡Derrota!</h3>
					<h5>Coordenadas correctas de {{ nombreCiudad }} - {{ territorioCiudad }}</h5>
					<h5>{{ resultadoCorrecto }}</h5>
				</div>
				<div class="d-block text-center">
					<b-button pill class="mt-3" variant="outline-success" @click="nuevaPartida">Nueva partida</b-button>
					<b-button pill class="mt-3" variant="outline-info" @click="ocultarDerrota">Cerrar</b-button>
				</div>
			</b-modal>
		</div>
		<div>
			<b-modal id="modalAyuda" size="lg" hide-footer title="Ayuda">
				<div class="d-block text-center">
					<h3>¿Cómo jugar?<br><br></h3>
					<h5>
					Debes acertar las coordenadas de la capital que se muestra, averiguando su latitud y longitud medidas en grados (redondeado a su valor más cercano).<br><br>
					La latitud tendrá un valor entre los 0 y los 90 grados, pudiendo ser Norte o Sur.<br><br>
					La longitud tendrá un valor entre los 0 y los 180 grados, pudiendo ser Este u Oeste.<br><br>
					El formato será siempre de dos dígitos para la latitud y tres dígitos para la longitud, además de la inicial del punto cardinal.<br><br>
					Por ejemplo, para Bogotá, cuyas coordenadas se aproximan a 5 grados Norte y 74 grados Oeste, la solución será: 05N074O.
					</h5>
				</div>
				<div class="d-block text-center">
					<b-button pill class="mt-3" variant="outline-info" @click="ocultarAyuda">Cerrar</b-button>
				</div>
			</b-modal>
		</div>
		<div>
			<b-modal id="modalInfo" size="lg" hide-footer title="Información">
				<div class="d-block text-center">
					<h3>Información<br><br></h3>
					<h5>
					Juego de coordenadas estilo <a href="https://www.powerlanguage.co.uk/wordle">Wordle</a>.<br><br>
					Juego subido a <a href="https://coordle.herokuapp.com/">Heroku</a>.<br><br>
					Proyecto disponible en <a href="https://github.com/PerseoFIC/coordle">GitHub</a>.<br><br>
					Imágenes de territorios de <a href="https://github.com/djaiss/mapsicon">MapsIcon</a>.
					</h5>
				</div>
				<div class="d-block text-center">
					<b-button pill class="mt-3" variant="outline-info" @click="ocultarInfo">Cerrar</b-button>
				</div>
			</b-modal>
		</div>
		<div>
			<b-modal id="modalEstadisticas" size="lg" hide-footer title="Estadísticas">
				<div class="d-block text-center">
					<h3>Racha de victorias</h3>
					<div class="row">
						<div class="col">
							<h1>{{ this.estadisticas.jugadas }}</h1>
							<h5>Jugadas</h5>
						</div>
						<div class="col">
							<h1>{{ this.estadisticas.ganadas }}</h1>
							<h5>Ganadas</h5>
						</div>
						<div class="col">
							<h1>{{ this.estadisticas.rachaActual }}</h1>
							<h5>Racha actual</h5>
						</div>
						<div class="col">
							<h1>{{ this.estadisticas.mejorRacha }}</h1>
							<h5>Mejor racha</h5>
						</div>
					</div>
					<h3>Victorias por intentos</h3>
					<div class="row justify-content-center">
						<div class="col-1">1: </div>
						<div class="col-6"><div class="progress"><div class="progress-bar bg-success" role="progressbar" :style="this.getProgreso(this.estadisticas.exitos[0])">{{ this.estadisticas.exitos[0] }}</div></div></div>
						<div class="col-1">{{ this.getPorcentajeSobreTotal(this.estadisticas.exitos[0]) }}</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-1">2: </div>
						<div class="col-6"><div class="progress"><div class="progress-bar bg-success" role="progressbar" :style="this.getProgreso(this.estadisticas.exitos[1])">{{ this.estadisticas.exitos[1] }}</div></div></div>
						<div class="col-1">{{ this.getPorcentajeSobreTotal(this.estadisticas.exitos[1]) }}</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-1">3: </div>
						<div class="col-6"><div class="progress"><div class="progress-bar bg-success" role="progressbar" :style="this.getProgreso(this.estadisticas.exitos[2])">{{ this.estadisticas.exitos[2] }}</div></div></div>
						<div class="col-1">{{ this.getPorcentajeSobreTotal(this.estadisticas.exitos[2]) }}</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-1">4: </div>
						<div class="col-6"><div class="progress"><div class="progress-bar bg-warning" role="progressbar" :style="this.getProgreso(this.estadisticas.exitos[3])">{{ this.estadisticas.exitos[3] }}</div></div></div>
						<div class="col-1">{{ this.getPorcentajeSobreTotal(this.estadisticas.exitos[3]) }}</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-1">5: </div>
						<div class="col-6"><div class="progress"><div class="progress-bar bg-warning" role="progressbar" :style="this.getProgreso(this.estadisticas.exitos[4])">{{ this.estadisticas.exitos[4] }}</div></div></div>
						<div class="col-1">{{ this.getPorcentajeSobreTotal(this.estadisticas.exitos[4]) }}</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-1">6: </div>
						<div class="col-6"><div class="progress"><div class="progress-bar bg-warning" role="progressbar" :style="this.getProgreso(this.estadisticas.exitos[5])">{{ this.estadisticas.exitos[5] }}</div></div></div>
						<div class="col-1">{{ this.getPorcentajeSobreTotal(this.estadisticas.exitos[5]) }}</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-1">X: </div>
						<div class="col-6"><div class="progress"><div class="progress-bar bg-danger" role="progressbar" :style="this.getProgreso(this.estadisticas.fracasos)">{{ this.estadisticas.fracasos }}</div></div></div>
						<div class="col-1">{{ this.getPorcentajeSobreTotal(this.estadisticas.fracasos) }}</div>
					</div>
				</div>
				<div class="d-block text-center">
					<b-button pill class="mt-3" variant="outline-info" @click="ocultarEstadisticas">Cerrar</b-button>
				</div>
			</b-modal>
		</div>
	</v-app>
</template>

<script>
import axios from 'axios';
export default {
	data() {
		return {
			idCoordleDia: 0,
			nombreCiudad: '',
			territorioCiudad: '',
			abreviaturaCiudad: '--',
			coordenadas: '',
			celdas: [],
			estado: [],
			direccionesPosibles: ['/icons/arrow-up-square-fill.svg', '/icons/arrow-up-right-square-fill.svg', '/icons/arrow-right-square-fill.svg', '/icons/arrow-down-right-square-fill.svg', '/icons/arrow-down-square-fill.svg', '/icons/arrow-down-left-square-fill.svg', '/icons/arrow-left-square-fill.svg', '/icons/arrow-up-left-square-fill.svg', '/icons/arrow-up-square-fill.svg', '/icons/question-square-fill.svg', '/icons/bullseye.svg'],
			direcciones: [9, 9, 9, 9, 9, 9],
			indiceCiudad: 0,
			indiceCeldas: 0,
			intento: { latitud: 0, longitud: 0, coordleDia: 0, etiquetas: '' },
			isDisabledNuevaPartida: true,
			partidaGuardada: {idCoordleDia: 0, nombreCiudad: '', territorioCiudad: '', abreviaturaCiudad: '--', celdas: [], estado: [], direcciones: [9, 9, 9, 9, 9, 9], indiceCiudad: 0, indiceCeldas: 0, isDisabledNuevaPartida: true},
			estadisticas: {jugadas: 0, ganadas: 0, rachaActual: 0, mejorRacha: 0, exitos: [0, 0, 0, 0, 0, 0], fracasos: 0},
			resultadoCorrecto: ''
		};
	},
	methods: {
		getURLImagen() {
			if (this.abreviaturaCiudad == '--') {
				axios.get('/coordledia')
				.then(response => {
					this.idCoordleDia = response.data.idCoordleDia
					this.nombreCiudad = response.data.nombre
					this.territorioCiudad = response.data.territorio
					this.abreviaturaCiudad = response.data.abreviatura
				})
				.catch(e => {
					console.log(e)
				})
			}
			return "/images/" + this.abreviaturaCiudad + "/256.png";
		},
		getProgreso(valor) {
			var maximo = 0;
			if (this.estadisticas.jugadas > 0) {
				maximo = Math.max(...this.estadisticas.exitos);
				maximo = Math.max(maximo, this.estadisticas.fracasos);
			}
			var progreso = Math.round(valor * 100 / maximo);
			return 'width: ' + progreso + '%';
		},
		getPorcentajeSobreTotal(valor) {
			var porcentaje = 0;
			if (this.estadisticas.jugadas > 0) {
				porcentaje = Math.round(valor * 100 / this.estadisticas.jugadas);
			}
			return '(' + porcentaje + '%)';
		},
		setValor(valor) {
			this.celdas[this.indiceCeldas++] = valor;
			this.coordenadas += valor;
		},
		enviar() {
			this.intento.latitud = this.coordenadas.substring(0, 2);
			this.intento.latitud *= this.coordenadas.charAt(2) == 'N' ? 1 : -1;
			this.intento.longitud = this.coordenadas.substring(3, 6);
			this.intento.longitud *= this.coordenadas.charAt(6) == 'E' ? 1 : -1;
			this.intento.coordleDia = this.idCoordleDia;
			this.intento.etiquetas = this.coordenadas;
			this.intento.numeroIntento = this.indiceCiudad;
			axios.post('/intento/', this.intento)
			.then(response => {
				this.estado[this.indiceCiudad * 8] = response.data.valor1;
				this.estado[parseInt(this.indiceCiudad * 8) + 1] = response.data.valor2;
				this.estado[parseInt(this.indiceCiudad * 8) + 2] = response.data.valor3;
				this.estado[parseInt(this.indiceCiudad * 8) + 3] = response.data.valor4;
				this.estado[parseInt(this.indiceCiudad * 8) + 4] = response.data.valor5;
				this.estado[parseInt(this.indiceCiudad * 8) + 5] = response.data.valor6;
				this.estado[parseInt(this.indiceCiudad * 8) + 6] = response.data.valor7;
				this.estado[parseInt(this.indiceCiudad * 8) + 7] = response.data.exito;
				if (response.data.exito) {
					this.direcciones[this.indiceCiudad] = 10;
					this.partidaGuardada.isDisabledNuevaPartida = false;
					this.estadisticas.ganadas++;
					this.estadisticas.jugadas = this.estadisticas.ganadas + this.estadisticas.fracasos;
					this.estadisticas.rachaActual++;
					if (this.estadisticas.rachaActual > this.estadisticas.mejorRacha) {
						this.estadisticas.mejorRacha = this.estadisticas.rachaActual;
					}
					this.estadisticas.exitos[this.indiceCiudad]++;
					this.guardarALocalStorage('estadisticas', this.estadisticas);
				} else {
					this.direcciones[this.indiceCiudad] = response.data.direccion;
					this.partidaGuardada.isDisabledNuevaPartida = true;
				}
				this.indiceCiudad++;
				this.indiceCeldas++;
				this.partidaGuardada.idCoordleDia = this.idCoordleDia;
				this.partidaGuardada.nombreCiudad = this.nombreCiudad;
				this.partidaGuardada.territorioCiudad = this.territorioCiudad;
				this.partidaGuardada.abreviaturaCiudad = this.abreviaturaCiudad;
				this.partidaGuardada.celdas = this.celdas;
				this.partidaGuardada.estado = this.estado;
				this.partidaGuardada.direcciones = this.direcciones;
				this.partidaGuardada.indiceCiudad = this.indiceCiudad;
				this.partidaGuardada.indiceCeldas = this.indiceCeldas;
				this.coordenadas = '';
				this.intento.latitud = 0;
				this.intento.longitud = 0;
				this.intento.etiquetas = '';
				if (response.data.exito) {
					document.getElementById('modalVictoria').style.display = 'inline';
					document.getElementById("modalVictoria").classList.add("show");
				} else if (this.indiceCiudad == 6) {
					this.resultadoCorrecto = response.data.coordenadasCorrectas;
					document.getElementById('modalDerrota').style.display = 'inline';
					document.getElementById("modalDerrota").classList.add("show");
					this.estadisticas.rachaActual = 0;
					this.estadisticas.fracasos++;
					this.estadisticas.jugadas = this.estadisticas.ganadas + this.estadisticas.fracasos;
					this.guardarALocalStorage('estadisticas', this.estadisticas);
					this.partidaGuardada.isDisabledNuevaPartida = false;
				}
				this.guardarALocalStorage('partidaGuardada', this.partidaGuardada);
			})
			.catch(e => {
				console.log(e)
			})
		},
		borrar() {
			this.celdas.pop();
			this.indiceCeldas--;
			this.coordenadas = this.coordenadas.slice(0, -1);
		},
		isDisabled(valor) {
			var modulo = this.indiceCeldas % 8;
			switch (valor) {
				case '0':
					return !this.isDisabledNuevaPartida || modulo == 2 || modulo == 6 || modulo == 7;
				case '1':
					return !this.isDisabledNuevaPartida || modulo == 2 || modulo == 6 || modulo == 7 ||
							(modulo == 5 && this.celdas[this.indiceCeldas - 2] == '1' && this.celdas[this.indiceCeldas - 1] == '8') ||
							(modulo == 1 && this.celdas[this.indiceCeldas - 1] == '9');
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
					return !this.isDisabledNuevaPartida || modulo == 2 || modulo == 6 || modulo == 7 || modulo == 3 ||
							(modulo == 1 && this.celdas[this.indiceCeldas - 1] == '9');
				case '9':
					return !this.isDisabledNuevaPartida || modulo == 2 || modulo == 6 || modulo == 7 || modulo == 3 ||
							(modulo == 1 && this.celdas[this.indiceCeldas - 1] == '9') ||
							(modulo == 4 && this.celdas[this.indiceCeldas - 1] == '1');
				case 'N':
				case 'S':
					return modulo != 2;
				case 'E':
				case 'O':
					return modulo != 6;
				case 'Enviar':
					return modulo != 7;
				case 'Borrar':
					return this.indiceCeldas == (8 * this.indiceCiudad);
			}
		},
		ocultarVictoria() {
			document.getElementById('modalVictoria').style.display = 'none';
			document.getElementById("modalVictoria").classList.remove("show");
			this.isDisabledNuevaPartida = false;
		},
		ocultarDerrota() {
			document.getElementById('modalDerrota').style.display = 'none';
			document.getElementById("modalDerrota").classList.remove("show");
			this.isDisabledNuevaPartida = false;
		},
		mostrarAyuda() {
			document.getElementById('modalAyuda').style.display = 'inline';
			document.getElementById("modalAyuda").classList.add("show");
		},
		ocultarAyuda() {
			document.getElementById('modalAyuda').style.display = 'none';
			document.getElementById("modalAyuda").classList.remove("show");
		},
		mostrarInfo() {
			document.getElementById('modalInfo').style.display = 'inline';
			document.getElementById("modalInfo").classList.add("show");
		},
		ocultarInfo() {
			document.getElementById('modalInfo').style.display = 'none';
			document.getElementById("modalInfo").classList.remove("show");
		},
		mostrarEstadisticas() {
			document.getElementById('modalEstadisticas').style.display = 'inline';
			document.getElementById("modalEstadisticas").classList.add("show");
		},
		ocultarEstadisticas() {
			document.getElementById('modalEstadisticas').style.display = 'none';
			document.getElementById("modalEstadisticas").classList.remove("show");
		},
		nuevaPartida() {
			axios.get('/coordledia/nuevo')
			.then(response => {
				this.idCoordleDia = response.data.idCoordleDia
				this.nombreCiudad = response.data.nombre
				this.territorioCiudad = response.data.territorio
				this.abreviaturaCiudad = response.data.abreviatura
				this.getURLImagen()
			})
			.catch(e => {
				console.log(e)
			})
			document.getElementById('modalVictoria').style.display = 'none';
			document.getElementById("modalVictoria").classList.remove("show");
			document.getElementById('modalDerrota').style.display = 'none';
			document.getElementById("modalDerrota").classList.remove("show");
			this.celdas = [];
			this.estado = [];
			this.direcciones = [9, 9, 9, 9, 9, 9];
			this.coordenadas = '';
			this.indiceCiudad = 0;
			this.indiceCeldas = 0;
			this.isDisabledNuevaPartida = true;
			this.guardarALocalStorage
			this.eliminarEnLocalStorage('partidaGuardada');
		},
		cargarDeLocalStorage(clave) {
			return JSON.parse(localStorage.getItem(clave));
		},
		guardarALocalStorage(clave, objeto) {
			localStorage.setItem(clave, JSON.stringify(objeto));
		},
		eliminarEnLocalStorage(clave) {
			localStorage.removeItem(clave);
		}
	},
	mounted() {
		if (localStorage.getItem('partidaGuardada')) {
			this.partidaGuardada = this.cargarDeLocalStorage('partidaGuardada');
			this.idCoordleDia = this.partidaGuardada.idCoordleDia;
			this.nombreCiudad = this.partidaGuardada.nombreCiudad;
			this.territorioCiudad = this.partidaGuardada.territorioCiudad;
			this.abreviaturaCiudad = this.partidaGuardada.abreviaturaCiudad;
			this.celdas = this.partidaGuardada.celdas;
			this.estado = this.partidaGuardada.estado;
			this.direcciones = this.partidaGuardada.direcciones;
			this.indiceCiudad = this.partidaGuardada.indiceCiudad;
			this.indiceCeldas = this.partidaGuardada.indiceCeldas;
			this.isDisabledNuevaPartida = this.partidaGuardada.isDisabledNuevaPartida;
		}
		if (localStorage.getItem('estadisticas')) {
			this.estadisticas = this.cargarDeLocalStorage('estadisticas');
		}
	}
}
</script>

<style>
	.container {
		width: 540px !important;
		padding: 12px !important;
	}
	#contenedor-titulo > h1 {
		font-size: 40px !important;
		font-weight: 700;
		right: 50%;
	}
	.centrado-vertical {
		vertical-align: middle;
	}
	.izquierda {
		top: 0px;
		padding-top: 24px;
	}
	.derecha {
		top: 0px;
		padding-top: 24px;
		right: 0px;
	}
	#icono-ayuda {
		padding-right: 12px;
	}
	#icono-estadisticas {
		padding-right: 76px;
	}
	#contenedor-busqueda {
		text-align: center;
	}
	.capital-busqueda {
		font-size: 24px;
		margin: 0;
	}
	.boton-direccion {
		border: 3px solid #ced4da !important;
		background-color: #e9ecef !important;
		padding: 8px 15px !important;
	}
	#contenedor-celdas {
		height: 400px;
		display: grid;
		box-sizing: border-box;
		margin: 0 auto;
	}
	.row > * {
		padding: 3px;
	}
	.form-control {
		background-image: none !important;
		height: 100%;
		width: 100%;
		padding: 0 !important;
		font-size: 40px !important;
		font-weight: 700;
		text-align: center;
		padding: 0;
		border-width: 3px;
	}
	.is-valid {
		color: white !important;
		background-color: forestgreen !important;
	}
	.is-invalid {
		color: white !important;
		background-color: dimgray !important;
	}
	.btn {
		padding: 8px 18px;
		font-size: 24px;
		font-weight: 700;
	}
	.btn-secondary:disabled {
		background-color: silver;
	}
	.btn-close {
		display: none;
	}
	.modal-header {
		display: inline;
	}
	.modal-title {
		text-align: center;
	}
	.progress {
		height: 1.5rem;
	}
</style>
