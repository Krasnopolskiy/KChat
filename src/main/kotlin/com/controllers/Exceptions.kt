package com.controllers

class InvalidRequestData : Exception("Invalid request data")
class UserNameIsNotFreeException : Exception("Name is already taken")
class InvalidCredentialsException : Exception("Invalid credentials")
