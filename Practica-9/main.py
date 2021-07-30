from zeep import Client
import json
# https://www.programcreek.com/python/example/106302/zeep.Client
# https://adriennedomingus.medium.com/using-zeep-to-make-soap-requests-in-python-c575ea0ee954
# https://stackoverflow.com/questions/4917006/string-to-dictionary-in-python
class Estudiante(object):

    def __init__(self,matricula,nombre, carrera):
        self.matricula = matricula
        self.nombre = nombre
        self.carrera = carrera

wsdlURL = 'http://localhost:7000/ws/EstudianteWebServices?wsdl'
client = Client(wsdlURL)

print('\t\tCliente SOAP')
print()
print('********Listado de estudiantes********')
print(client.service.getListaEstudiante())

print()
print('********Consultar Estudiante********')
print(client.service.getEstudiante(20011136))


print()
print('********Crear nuevo Estudiante********')
estudiante = Estudiante(1111, "nuevoEstudiante", "ISC")
jsonString = json.dumps(estudiante.__dict__)
# print(json.loads(jsonString))
print(client.service.crearEstudiante(json.loads(jsonString)))


print('********Listado de estudiantes con otro nuevo********')
print(client.service.getListaEstudiante())

print()
print('********Eliminar Estudiante********')
# Metodo no existia en javalin-demo. Se tuvo que crear:
# @WebMethod
#     public boolean eliminandoEstudiante(int matricula){
#         return fakeServices.eliminandoEstudiante(matricula);
#     }
print(client.service.eliminandoEstudiante(1111))

print('Verificacion de que se borro:')
print(client.service.getListaEstudiante())