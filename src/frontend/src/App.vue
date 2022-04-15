<template>
	<v-app>
		<b-container id="contenedor-titulo" class="text-center position-relative border-bottom mb-3">
			<h1 class="text-center mb-0 centrado-vertical">COORDLE</h1>
			<div id="icono-ayuda" class="position-absolute derecha">
				<img src="/icons/patch-question.svg" width="32" height="32" @click="mostrarAyuda">
			</div>
		</b-container>
		<b-container id="contenedor-busqueda">
			<img width="128" height="128" :src="getURLImagen()"/>
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
				</div>
				<div class="d-block text-center">
					<b-button pill class="mt-3" variant="outline-success" @click="nuevaPartida">Nueva partida</b-button>
					<b-button pill class="mt-3" variant="outline-info" @click="ocultarDerrota">Cerrar</b-button>
				</div>
			</b-modal>
		</div>
		<div>
			<b-modal id="modalAyuda" size="lg" ref="modal-ayuda" hide-footer title="Ayuda">
				<div class="d-block text-center">
					<h3>¿Cómo jugar?</h3>
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
			direccionesPosibles: ['/icons/arrow-up-square-fill.svg', '/icons/arrow-up-right-square-fill.svg', '/icons/arrow-right-square-fill.svg', '/icons/arrow-down-right-square-fill.svg', '/icons/arrow-down-square-fill.svg', '/icons/arrow-down-left-square-fill.svg', '/icons/arrow-left-square-fill.svg', '/icons/arrow-up-left-square-fill.svg', '/icons/arrow-up-square-fill.svg', '/icons/question-square-fill.svg'],
			direcciones: [9, 9, 9, 9, 9, 9],
			indiceCeldas: 0,
			indiceCiudad: 0,
			intento: { latitud: 0, longitud: 0, coordleDia: 0, etiquetas: '' },
			errores: [],
			isDisabledNuevaPartida: true
		};
	},
	methods: {
		/*global getURLImagen:true*/
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
					this.errores.push(e)
				})
			}
			return "/images/" + this.abreviaturaCiudad + "/256.png";
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
				this.direcciones[this.indiceCiudad] = response.data.direccion;
				this.indiceCiudad++;
				this.indiceCeldas++;
				this.coordenadas = '';
				this.intento.latitud = 0;
				this.intento.longitud = 0;
				this.intento.etiquetas = '';
				if (response.data.exito) {
					document.getElementById('modalVictoria').style.display = 'inline';
					document.getElementById("modalVictoria").classList.add("show");
				} else if (this.indiceCiudad == 6) {
					document.getElementById('modalDerrota').style.display = 'inline';
					document.getElementById("modalDerrota").classList.add("show");
				}
			})
			.catch(e => {
				this.errores.push(e)
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
					return modulo == 2 || modulo == 6 || modulo == 7;
				case '1':
					return modulo == 2 || modulo == 6 || modulo == 7 ||
							(modulo == 5 && this.celdas[this.indiceCeldas - 2] == '1' && this.celdas[this.indiceCeldas - 1] == '8') ||
							(modulo == 1 && this.celdas[this.indiceCeldas - 1] == '9');
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
					return modulo == 2 || modulo == 6 || modulo == 7 || modulo == 3 || (modulo == 1 && this.celdas[this.indiceCeldas - 1] == '9');
				case '9':
					return modulo == 2 || modulo == 6 || modulo == 7 || modulo == 3 || (modulo == 1 && this.celdas[this.indiceCeldas - 1] == '9') ||
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
		nuevaPartida() {
			axios.get('/coordledia/nuevo')
			.then(response => {
				this.idCoordleDia = response.data.idCoordleDia
				this.nombreCiudad = response.data.nombre
				this.territorioCiudad = response.data.territorio
				this.abreviaturaCiudad = response.data.abreviatura
				getURLImagen()
			})
			.catch(e => {
				this.errores.push(e)
			})
			document.getElementById('modalVictoria').style.display = 'none';
			document.getElementById("modalVictoria").classList.remove("show");
			document.getElementById('modalDerrota').style.display = 'none';
			document.getElementById("modalDerrota").classList.remove("show");
			this.celdas = [];
			this.estado = [];
			this.direcciones = [9, 9, 9, 9, 9, 9];
			this.coordenadas = '';
			this.indiceCeldas = 0;
			this.indiceCiudad = 0;
			this.isDisabledNuevaPartida = true;
		}
	}
}
</script>

<style>
	.container {
		max-width: 540px !important;
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
	.derecha {
		top: 0px;
		right: 0px;
	}
	#icono-ayuda {
		padding-top: 24px;
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
		width: 540px;
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
</style>
