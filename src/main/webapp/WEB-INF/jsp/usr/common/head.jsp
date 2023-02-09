<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pageTitle }</title>
<script src="https://unpkg.com/tailwindcss-jit-cdn"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
<link rel="stylesheet" href="/resource/common.css" />
</head>
<body>
	<header>
		<div class="h-20 flex container mx-auto text-4xl">
			<a class="h-full px-3 flex items-center" href="#"><span>로고</span></a>
			<div class="flex-grow"></div>
			<ul class="flex">
				<li class="hover:underline"><a
					class="h-full px-3 flex items-center" href="/"><span>HOME</span></a></li>
				<li class="hover:underline"><a
					class="h-full px-3 flex items-center" href="/usr/article/list"><span>LIST</span></a></li>
				<li class="hover:underline"><a
					class="h-full px-3 flex items-center" href="/usr/member/login"><span>LOGIN</span></a></li>
			</ul>
		</div>
	</header>
	<section class="my-3 text-2xl">
		<div class="container mx-auto px-3"></div>
			<h1 >${pageTitle }&nbsp;Page</h1>
	</section>
	<main>